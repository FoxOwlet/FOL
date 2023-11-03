package com.foxowlet.fol.interpreter.model.memory;

public interface MemoryLocation {
    byte[] read();

    void read(byte[] data);

    void write(byte[] data);

    MemoryLocation slice(int offset, int size);

    int address();
}
