package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.FunctionCall;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Function;
import com.foxowlet.fol.interpreter.model.Value;

public class FunctionCallInterpreter implements ExpressionInterpreter<FunctionCall> {
    @Override
    public Object interpret(FunctionCall expression, InterpretationContext context) {
        Value value = context.interpret(expression.target(), Value.class);
        Function function = ReflectionUtils.as(value.value(), Function.class);
        return context.interpret(function.body());
    }
}
