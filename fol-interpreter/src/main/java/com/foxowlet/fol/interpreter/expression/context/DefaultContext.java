package com.foxowlet.fol.interpreter.expression.context;

public record DefaultContext() implements ExpressionContext {
    @Override
    public boolean fallbackToDefault() {
        return false;
    }
}
