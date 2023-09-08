package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.interpreter.predefined.PredefinedPreprocessor;

public class InterpreterConfiguration {
    private int memoryLimit = 1024;
    private ContextPreprocessor predefinedProcessor = new PredefinedPreprocessor();

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public ContextPreprocessor getPredefinedProcessor() {
        return predefinedProcessor;
    }

    public InterpreterConfiguration withMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
        return this;
    }

    public InterpreterConfiguration withPredefinedProcessor(ContextPreprocessor predefinedProcessor) {
        this.predefinedProcessor = predefinedProcessor;
        return this;
    }
}
