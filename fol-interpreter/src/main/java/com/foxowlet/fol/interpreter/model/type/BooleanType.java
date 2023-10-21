package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.exception.TypeException;

public class BooleanType implements TypeDescriptor {
    private static final byte[] TRUE = new byte[] {1};
    private static final byte[] FALSE = new byte[] {0};

    @Override
    public String name() {
        return "Bool";
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof Boolean b) {
            return b ? TRUE : FALSE;
        }
        throw new TypeException(value, Boolean.class);
    }

    @Override
    public Boolean decode(byte[] data) {
        return data[0] != 0;
    }
}
