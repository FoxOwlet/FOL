package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.expression.context.DefaultContext;
import com.foxowlet.fol.interpreter.expression.context.ExpressionContext;

public interface ExpressionInterpreter<T extends Expression> {
    default Object interpretRaw(Expression expression, InterpretationContext context) {
        //noinspection unchecked
        return interpret((T) expression, context);
    }

    Object interpret(T expression, InterpretationContext context);

    default Class<? extends ExpressionContext> supportedContext() {
        return DefaultContext.class;
    }
}
