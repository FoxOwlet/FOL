package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Equals;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.BooleanType;

public class EqualsInterpreter implements ExpressionInterpreter<Equals> {
    @Override
    public Value interpret(Equals expression, InterpretationContext context) {
        Value left = context.interpret(expression.left());
        Value right = context.interpret(expression.right());
        if (!left.type().isCompatibleWith(right.type())) {
            throw new IncompatibleTypeException(left.value(), right.type());
        }
        return new Container(left.value().equals(right.value()), new BooleanType());
    }
}
