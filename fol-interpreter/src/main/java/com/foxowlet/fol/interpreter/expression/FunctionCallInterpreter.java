package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.FunctionCall;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.InvalidFunctionCall;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Callable;
import com.foxowlet.fol.interpreter.model.FunctionParameter;
import com.foxowlet.fol.interpreter.model.Value;

import java.util.List;

public class FunctionCallInterpreter implements ExpressionInterpreter<FunctionCall> {
    @Override
    public Object interpret(FunctionCall expression, InterpretationContext context) {
        Value value = context.interpret(expression.target(), Value.class);
        Callable function = ReflectionUtils.as(value.value(), Callable.class);
        checkParameters(function.params(), expression.arguments());
        // first convert all the arguments (actuals)
        // this must happen in the outer scope (e.g. if the expression contains variable declaration)
        List<Value> arguments = convertArguments(expression.arguments(), context);
        // then create a fresh scope for function's formal parameters and bind them
        context.enterScope();
        // then interpret body using bound params
        Object returnValue = function.call(arguments, context);
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
}
