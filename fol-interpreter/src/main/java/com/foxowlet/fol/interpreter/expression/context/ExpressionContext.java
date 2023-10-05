package com.foxowlet.fol.interpreter.expression.context;

public interface ExpressionContext {
    default boolean fallbackToDefault() {
        return true;
    }
}
