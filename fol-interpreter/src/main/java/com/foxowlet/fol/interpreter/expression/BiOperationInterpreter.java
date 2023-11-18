package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.BiOperation;
import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

abstract class BiOperationInterpreter<T extends Expression & BiOperation, R> implements ExpressionInterpreter<T> {
    @Override
    public Value interpret(T expression, InterpretationContext context) {
        Value left = context.interpret(expression.left(), Value.class);
        Value right = context.interpret(expression.right(), Value.class);
        TypeDescriptor rightType = right.type();
        if (!left.type().isCompatibleWith(rightType)) {
            throw new IncompatibleTypeException(left, rightType);
        }
        R result = op(value(left), value(right));
        return new Container(typed(result, rightType), rightType);
    }

    protected abstract R value(Value value);
    protected abstract R op(R left, R right);

    private Object typed(R value, TypeDescriptor type) {
        // addition is performed with doubles
        // this will downcast to correct Java type if needed
        return type.decode(type.encode(value));
    }
}
