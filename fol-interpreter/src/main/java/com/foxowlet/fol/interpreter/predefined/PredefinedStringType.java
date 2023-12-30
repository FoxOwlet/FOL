package com.foxowlet.fol.interpreter.predefined;

import com.foxowlet.fol.interpreter.ContextPreprocessor;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.type.StringType;

final class PredefinedStringType implements ContextPreprocessor {
    @Override
    public void preprocess(InterpretationContext context) {
        StringType stringType = context.makeString();
        context.registerSymbol(stringType.name(), stringType);
    }
}
