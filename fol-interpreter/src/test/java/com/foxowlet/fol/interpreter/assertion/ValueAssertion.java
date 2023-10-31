package com.foxowlet.fol.interpreter.assertion;

import com.foxowlet.fol.interpreter.model.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ValueAssertion {
    private final Value value;

    ValueAssertion(Value value) {
        this.value = value;
    }

    public void is(Object expected) {
        assertEquals(expected, value.value());
    }
}
