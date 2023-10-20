package com.foxowlet.fol.interpreter.model;

public interface MemoryLocation {
    void read(byte[] data);

    void write(byte[] data);

    MemoryLocation slice(int offset, int size);
}
