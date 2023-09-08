package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.interpreter.predefined.PredefinedChain;
import com.foxowlet.fol.interpreter.predefined.PredefinedProcessor;

public class InterpreterConfiguration {
    private int memoryLimit = 1024;
    private PredefinedProcessor predefinedProcessor = new PredefinedChain();

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public PredefinedProcessor getPredefinedProcessor() {
        return predefinedProcessor;
    }

    public InterpreterConfiguration withMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
        return this;
    }

    public InterpreterConfiguration withPredefinedProcessor(PredefinedProcessor predefinedProcessor) {
        this.predefinedProcessor = predefinedProcessor;
        return this;
    }
}
