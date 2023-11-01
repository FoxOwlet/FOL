package com.foxowlet.fol.interpreter.model.memory;

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

    @Override
    public MemoryLocation slice(int offset, int size) {
        if (offset + size > this.size) {
            throw new IllegalStateException("Memory access out of bounds(block size: %d, offset: %d, slice size: %d)"
                    .formatted(this.size, offset, size));
        }
        return new MemoryBlock(memory, address + offset, size);
    }

    @Override
    public int address() {
        return address;
    }

    private void checkSize(byte[] data) {
        if (data.length != size) {
            throw new IllegalStateException("Memory block size mismatch, expected %d, got %d"
                    .formatted(size, data.length));
        }
    }
}
