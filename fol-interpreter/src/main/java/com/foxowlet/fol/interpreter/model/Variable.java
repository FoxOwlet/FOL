package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

public record Variable(MemoryLocation memory, String name, TypeDescriptor type) implements Value {
    @Override
    public Object value() {
        byte[] data = memory.read();
        return type.decode(data);
    }

    public void write(Object value) {
        byte[] data = type.encode(value);
        memory.write(data);
    }
}
