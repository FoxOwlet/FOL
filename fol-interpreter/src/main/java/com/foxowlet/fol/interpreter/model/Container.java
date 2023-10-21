package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.interpreter.model.memory.DummyMemoryLocation;
import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

public record Container(Object value, TypeDescriptor type) implements Value {
    @Override
    public MemoryLocation memory() {
        return new DummyMemoryLocation();
    }
}
