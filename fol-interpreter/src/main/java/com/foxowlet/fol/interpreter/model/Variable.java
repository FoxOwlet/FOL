package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.emulator.Memory;

public record Variable(Memory memory, int address, String name, Type type) implements Value {
    @Override
    public Object value() {
        byte[] data = new byte[type.size()];
        memory.read(address, data);
        return type.decode(data);
    }

    public void write(Object value) {
        byte[] data = type.encode(value);
        memory.write(address, data);
    }
}
