package com.foxowlet.fol.interpreter.model.type;

public class UnitType implements TypeDescriptor {
    @Override
    public String name() {
        return "Unit";
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
}
