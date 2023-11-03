package com.foxowlet.fol.interpreter.model.memory;

public class DummyMemoryLocation implements MemoryLocation {
    @Override
    public byte[] read() {
        throw new UnsupportedOperationException();
    }

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

    @Override
    public int address() {
        throw new UnsupportedOperationException();
    }
}
