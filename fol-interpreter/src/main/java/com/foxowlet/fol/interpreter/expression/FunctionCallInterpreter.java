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
        context.enterScope();
        bindParams(function.params(), expression.arguments(), context);
        Object returnValue = context.interpret(function.body());
        context.exitScope();
        return returnValue;
    }

    private void bindParams(List<FunctionParameter> params,
                            List<Expression> arguments,
                            InterpretationContext context) {
        Iterator<FunctionParameter> paramsIterator = params.iterator();
        Iterator<Expression> argsIterator = arguments.iterator();
        while (paramsIterator.hasNext() && argsIterator.hasNext()) {
            FunctionParameter formal = paramsIterator.next();
            MemoryBlock memoryBlock = context.allocateMemory(formal.type().size());
            Variable variable = new Variable(memoryBlock, formal.name(), formal.type());
            context.registerSymbol(formal.name(), variable);
            Expression actual = argsIterator.next();
            Value value = context.interpret(actual, Value.class);
            variable.write(value.value());
        }
        if (paramsIterator.hasNext() || argsIterator.hasNext()) {
            throw new InvalidFunctionCall("Arguments count mismatch, expected %d, got %d"
                    .formatted(params.size(), arguments.size()));
        }
    }
}
