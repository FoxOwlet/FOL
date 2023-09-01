package com.foxowlet.fol.emulator.memory;

public class PhysicalMemory implements Memory {
    private final byte[] memory;

    public PhysicalMemory(int size) {
        memory = new byte[size];
    }

    @Override
    public void write(int address, byte[] data) {
        System.arraycopy(data, 0, memory, address, data.length);
    }

    @Override
    public void read(int address, byte[] data) {
        System.arraycopy(memory, address, data, 0, data.length);
    }
}
