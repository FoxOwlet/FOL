package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.FunctionCall;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.InvalidFunctionCall;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.*;

import java.util.Iterator;
import java.util.List;

public class FunctionCallInterpreter implements ExpressionInterpreter<FunctionCall> {
    @Override
    public Object interpret(FunctionCall expression, InterpretationContext context) {
        Value value = context.interpret(expression.target(), Value.class);
        Function function = ReflectionUtils.as(value.value(), Function.class);
        checkParameters(function.params(), expression.arguments());
        // first convert all the arguments (actuals)
        // this must happen in the outer scope (e.g. if the expression contains variable declaration)
        List<Value> arguments = convertArguments(expression.arguments(), context);
        // then create a fresh scope for function's formal parameters and bind them
        context.enterScope();
        bindParams(function.params(), arguments, context);
        // then interpret body using bound params
        Object returnValue = context.interpret(function.body());
        context.exitScope();
        return returnValue;
    }

    private void checkParameters(List<FunctionParameter> params, List<Expression> arguments) {
        if (params.size() != arguments.size()) {
            throw new InvalidFunctionCall("Arguments count mismatch, expected %d, got %d"
                    .formatted(params.size(), arguments.size()));
        }
    }

    private List<Value> convertArguments(List<Expression> arguments, InterpretationContext context) {
        return arguments.stream()
                .map(arg -> convertArgument(arg, context))
                .toList();
    }

    private Value convertArgument(Expression argument, InterpretationContext context) {
        return context.interpret(argument, Value.class);
    }

    private void bindParams(List<FunctionParameter> params,
                            List<Value> arguments,
                            InterpretationContext context) {
        Iterator<FunctionParameter> paramsIterator = params.iterator();
        Iterator<Value> argumentsIterator = arguments.iterator();
        while (paramsIterator.hasNext() && argumentsIterator.hasNext()) {
            Variable formal = makeParameter(paramsIterator.next(), context);
            bind(formal, argumentsIterator.next());
        }
        if (paramsIterator.hasNext() || argumentsIterator.hasNext()) {
            // not an InterpreterException as we already checked the lengths before
            // this should be never reached during a normal execution and acts like a guard check
            throw new IllegalStateException("Parameters and arguments length mismatch");
        }
    }

    private Variable makeParameter(FunctionParameter formal, InterpretationContext context) {
        MemoryBlock memoryBlock = context.allocateMemory(formal.type().size());
        Variable variable = new Variable(memoryBlock, formal.name(), formal.type());
        context.registerSymbol(formal.name(), variable);
        return variable;
    }

    private void bind(Variable variable, Value value) {
        variable.write(value.value());
    }
}
