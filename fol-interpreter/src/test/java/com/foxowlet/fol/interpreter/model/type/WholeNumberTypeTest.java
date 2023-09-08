package com.foxowlet.fol.interpreter.model.type;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

abstract class WholeNumberTypeTest<T extends Number> {
    protected final Random rnd;

    protected WholeNumberTypeTest() {
        this(new Random());
    }

    protected WholeNumberTypeTest(long rndSeed) {
        this(new Random(rndSeed));
    }

    protected WholeNumberTypeTest(Random rnd) {
        this.rnd = rnd;
    }

    protected abstract WholeNumberType<T> type();
    protected abstract T value();

    @RepeatedTest(1000)
    void decode_shouldReturnProperValue() {
        WholeNumberType<T> type = type();
        T expected = value();

        byte[] data = type.encode(expected);
        Object actual = type.decode(data);

        assertEquals(expected, actual);
    }
}