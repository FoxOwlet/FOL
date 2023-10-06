package com.foxowlet.fol.interpreter.expression.context;

public class DefaultContext implements ExpressionContext {
    @Override
    public boolean fallbackToDefault() {
        return false;
    }
}
