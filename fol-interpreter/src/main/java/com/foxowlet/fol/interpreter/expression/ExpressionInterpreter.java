package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.Interpreter;

public interface ExpressionInterpreter<T extends Expression> {
    default Object interpretRaw(Expression expression, Interpreter.Context context) {
        return interpret((T) expression, context);
    }

    Object interpret(T expression, Interpreter.Context context);
}
