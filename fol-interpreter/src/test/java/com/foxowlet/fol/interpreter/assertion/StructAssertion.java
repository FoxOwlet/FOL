package com.foxowlet.fol.interpreter.assertion;

import com.foxowlet.fol.interpreter.model.type.StructType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public final class StructAssertion {
    private final byte[] struct;
    private final StructType type;
    private int fieldOffset;

    StructAssertion(byte[] struct, StructType type) {
        this.struct = struct;
        this.type = type;
        this.fieldOffset = 0;
    }

    public StructAssertion hasField(Object value, TypeDescriptor type) {
        byte[] field = type.encode(value);
        return hasField(field);
    }

    public StructAssertion hasField(byte[] field) {
        assertArrayEquals(field, Arrays.copyOfRange(struct, fieldOffset, fieldOffset + field.length));
        fieldOffset += field.length;
        return this;
    }

    public StructTypeAssertion type() {
        return new StructTypeAssertion(this, type);
    }
}
