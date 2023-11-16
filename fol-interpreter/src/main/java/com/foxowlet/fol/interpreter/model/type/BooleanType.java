package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.internal.ReflectionUtils;

public final class BooleanType implements TypeDescriptor {
    private static final byte[] TRUE = new byte[]{1};
    private static final byte[] FALSE = new byte[]{0};

    @Override
    public String name() {
        return "Boolean";
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public byte[] encode(Object value) {
        boolean val = ReflectionUtils.as(value, Boolean.class);
        return val ? TRUE : FALSE;
    }

    @Override
    public Object decode(byte[] data) {
        return data[0] != 0;
    }
}
