package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Subtraction;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

public class SubtractionInterpreter implements ExpressionInterpreter<Subtraction> {
    @Override
    public Value interpret(Subtraction expression, InterpretationContext context) {
        Value left = context.interpret(expression.left(), Value.class);
        Value right = context.interpret(expression.right(), Value.class);
        TypeDescriptor rightType = right.type();
        if (!left.type().isCompatibleWith(rightType)) {
            throw new IncompatibleTypeException(left, rightType);
        }
        // assume leftType == rightType for now
        // TODO: support implicit conversions and type widening?
        double result = asDouble(left) - asDouble(right);
        return new Container(typed(result, rightType), rightType);
    }

    private double asDouble(Value value) {
        return ReflectionUtils.as(value.value(), Number.class).doubleValue();
    }

    private Object typed(double value, TypeDescriptor type) {
        // addition is performed with doubles
        // this will downcast to correct Java type if needed
        return type.decode(type.encode(value));
    }
}
