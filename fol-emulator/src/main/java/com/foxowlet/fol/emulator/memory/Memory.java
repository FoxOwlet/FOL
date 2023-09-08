package com.foxowlet.fol.emulator.memory;

public interface Memory {
    void write(int address, byte[] data);
    void read(int address, byte[] data);
}
