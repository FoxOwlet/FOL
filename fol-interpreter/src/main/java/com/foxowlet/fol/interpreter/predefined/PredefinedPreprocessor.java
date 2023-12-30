package com.foxowlet.fol.interpreter.predefined;

import com.foxowlet.fol.interpreter.ContextPreprocessor;
import com.foxowlet.fol.interpreter.InterpretationContext;

import java.util.List;

public class PredefinedPreprocessor implements ContextPreprocessor {
    private final List<ContextPreprocessor> processors;

    public PredefinedPreprocessor() {
        processors = List.of(
                new PredefinedTypes(),
                new PredefinedStringType(),
                new PredefinedFunctions()
        );
    }

    @Override
    public void preprocess(InterpretationContext context) {
        processors.forEach(processor -> processor.preprocess(context));
    }
}
