package com.foxowlet.fol.interpreter.model.type;

import java.util.Objects;

public class UnitType implements TypeDescriptor {
    private static final String NAME = "Unit";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] encode(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object decode(byte[] data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        return object != null && getClass() == object.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(NAME);
    }
}
