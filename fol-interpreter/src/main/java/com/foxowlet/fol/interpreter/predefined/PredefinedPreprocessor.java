package com.foxowlet.fol.interpreter.predefined;

import com.foxowlet.fol.interpreter.ContextPreprocessor;
import com.foxowlet.fol.interpreter.Interpreter;

import java.util.List;

public class PredefinedPreprocessor implements ContextPreprocessor {
    private final List<ContextPreprocessor> processors;

    public PredefinedPreprocessor() {
        processors = List.of(
                new PredefinedTypes()
        );
    }

    @Override
    public void preprocess(Interpreter.Context context) {
        processors.forEach(processor -> processor.preprocess(context));
    }
}
