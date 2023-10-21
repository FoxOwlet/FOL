package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Value;

public interface ExpressionInterpreter<T extends Expression> {
    default Value interpretRaw(Expression expression, InterpretationContext context) {
        //noinspection unchecked
        return interpret((T) expression, context);
    }

    Value interpret(T expression, InterpretationContext context);
}
