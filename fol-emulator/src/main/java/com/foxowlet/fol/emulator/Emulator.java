package com.foxowlet.fol.emulator;

import com.foxowlet.fol.emulator.memory.Memory;
import com.foxowlet.fol.emulator.memory.PhysicalMemory;

public class Emulator {

    public Memory allocate(int amount) {
        return new PhysicalMemory(amount);
    }
}
