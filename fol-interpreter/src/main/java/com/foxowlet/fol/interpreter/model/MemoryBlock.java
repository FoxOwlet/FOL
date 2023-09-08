package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.emulator.memory.Memory;

public class MemoryBlock implements MemoryLocation {
    private final Memory memory;
    private final int address;
    private final int size;

    public MemoryBlock(Memory memory, int address, int size) {
        this.memory = memory;
        this.address = address;
        this.size = size;
    }

    @Override
    public void read(byte[] data) {
        checkSize(data);
        memory.read(address, data);
    }

    @Override
    public void write(byte[] data) {
        checkSize(data);
        memory.write(address, data);
    }

    private void checkSize(byte[] data) {
        if (data.length != size) {
            throw new IllegalStateException("Memory block size mismatch, expected %d, got %d"
                    .formatted(size, data.length));
        }
    }
}
