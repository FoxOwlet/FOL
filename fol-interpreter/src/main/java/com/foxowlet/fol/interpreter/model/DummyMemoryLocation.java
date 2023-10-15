package com.foxowlet.fol.interpreter.model;

public class DummyMemoryLocation implements MemoryLocation {
    @Override
    public void read(byte[] data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(byte[] data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MemoryLocation slice(int offset, int size) {
        throw new UnsupportedOperationException();
    }
}
