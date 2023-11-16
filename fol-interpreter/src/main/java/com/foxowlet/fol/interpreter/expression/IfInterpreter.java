package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.If;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.TypeException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.BooleanType;

public class IfInterpreter implements ExpressionInterpreter<If> {
    @Override
    public Value interpret(If expression, InterpretationContext context) {
        Value value = context.interpret(expression.condition());
        if (!(value.type() instanceof BooleanType)) {
            throw new TypeException(value.value(), Boolean.class);
        }
        boolean condition = ReflectionUtils.as(value.value(), Boolean.class);
        Expression branch = condition ? expression.thenBranch() : expression.elseBranch();
        return context.interpret(branch);
    }
}
