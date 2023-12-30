package com.foxowlet.fol.interpreter.assertion;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringAssertion {
    private final String actual;

    public StringAssertion(String actual) {
        this.actual = actual;
    }

    public StringAssertion is(String expected) {
        assertEquals(expected, actual);
        return this;
    }
}
