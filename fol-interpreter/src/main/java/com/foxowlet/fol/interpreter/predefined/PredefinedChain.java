package com.foxowlet.fol.interpreter.predefined;

import com.foxowlet.fol.interpreter.Interpreter;

import java.util.List;

public class PredefinedChain implements PredefinedProcessor {
    private final List<PredefinedProcessor> processors;

    public PredefinedChain() {
        processors = List.of(
                new PredefinedTypes()
        );
    }

    @Override
    public void process(Interpreter.Context context) {
        processors.forEach(processor -> processor.process(context));
    }
}
