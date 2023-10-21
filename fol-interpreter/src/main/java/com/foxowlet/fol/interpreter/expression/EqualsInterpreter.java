package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Equals;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.BooleanType;

public class EqualsInterpreter implements ExpressionInterpreter<Equals> {
    @Override
    public Object interpret(Equals expression, InterpretationContext context) {
        Value left = context.interpret(expression.left(), Value.class);
        Value right = context.interpret(expression.right(), Value.class);
        if (!right.type().isCompatibleWith(left.type())) {
            throw new IncompatibleTypeException(left, right.type());
        }
        // TODO: move equality logic to the language itself
        return new Container(left.value().equals(right.value()), new BooleanType());
    }
}
