package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.interpreter.model.IntType;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class IntTypeTest {
    @RepeatedTest(1000)
    void decode_shouldReturnProperValue() {
        IntType type = new IntType();

        int expected = new Random().nextInt();
        byte[] data = type.encode(expected);
        Integer actual = type.decode(data);

        assertEquals(expected, actual);
    }
}