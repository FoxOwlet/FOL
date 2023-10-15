package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Addition;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.IntType;

public class AdditionInterpreter implements ExpressionInterpreter<Addition> {
    @Override
    public Object interpret(Addition expression, InterpretationContext context) {
        Value left = context.interpret(expression.left(), Value.class);
        Value right = context.interpret(expression.right(), Value.class);
        return new Container((int) left.value() + (int) right.value(), new IntType());
    }
}
