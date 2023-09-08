package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.interpreter.model.type.Type;

public record Variable(MemoryLocation memory, String name, Type type) implements Value {
    @Override
    public Object value() {
        byte[] data = new byte[type.size()];
        memory.read(data);
        return type.decode(data);
    }

    public void write(Object value) {
        byte[] data = type.encode(value);
        memory.write(data);
    }
}
