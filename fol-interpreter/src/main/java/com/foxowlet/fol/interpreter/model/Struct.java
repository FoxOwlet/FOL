package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.type.StructType;

public record Struct(MemoryLocation memory, StructType type) implements Value {
    @Override
    public byte[] value() {
        byte[] bytes = new byte[type.size()];
        memory.read(bytes);
        return type.decode(bytes);
    }
}
