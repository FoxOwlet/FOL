package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.If;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.BooleanType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

public class IfInterpreter implements ExpressionInterpreter<If> {
    private static final TypeDescriptor CONDITION_TYPE = new BooleanType();

    @Override
    public Value interpret(If expression, InterpretationContext context) {
        Value value = context.interpret(expression.condition());
        if (!CONDITION_TYPE.isCompatibleWith(value.type())) {
            throw new IncompatibleTypeException(value.value(), CONDITION_TYPE);
        }
        boolean condition = ReflectionUtils.as(value.value(), Boolean.class);
        Expression branch = condition ? expression.thenBranch() : expression.elseBranch();
        return context.interpret(branch);
    }
}
