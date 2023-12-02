package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.exception.TypeException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;

import java.util.Arrays;
import java.util.Objects;

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
        boolean val = ReflectionUtils.as(value, Boolean.class, TypeException::new);
        return val ? TRUE : FALSE;
    }

    @Override
    public Object decode(byte[] data) {
        if (data.length != size()) {
            throw new TypeException(Arrays.toString(data), Boolean.class);
        }
        return data[0] != 0;
    }

    @Override
    public boolean isCompatibleWith(TypeDescriptor other) {
        return equals(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj != null && getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClass());
    }
}
