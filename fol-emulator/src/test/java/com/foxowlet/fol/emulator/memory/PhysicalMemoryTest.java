package com.foxowlet.fol.emulator.memory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicalMemoryTest {
    @Test
    void read_shouldReturnDataStoredByWrite() {
        PhysicalMemory memory = new PhysicalMemory(100);
        byte[] expected = new byte[] {1, 2, 3, 4};
        byte[] actual = new byte[4];

        memory.write(10, expected);
        memory.read(10, actual);

        assertArrayEquals(expected, actual);
    }
}