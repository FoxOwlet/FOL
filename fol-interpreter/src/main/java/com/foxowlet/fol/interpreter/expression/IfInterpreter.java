package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.If;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Value;

public class IfInterpreter implements ExpressionInterpreter<If> {
    @Override
    public Value interpret(If expression, InterpretationContext context) {
        Value value = context.interpret(expression.condition(), Value.class);
        boolean condition = ReflectionUtils.as(value.value(), Boolean.class);
        Expression result = condition ? expression.thenBranch() : expression.elseBranch();
        return context.interpret(result);
    }
}
