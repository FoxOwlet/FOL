package com.foxowlet.fol.interpreter.assertion;

import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ValueAssertion {
    private final Value value;

    ValueAssertion(Value value) {
        this.value = value;
    }

    public ValueAssertion is(Object expected) {
        assertEquals(expected, value.value());
        return this;
    }

    public ValueAssertion hasType(TypeDescriptor type) {
        assertEquals(type, value.type());
        return this;
    }
}
