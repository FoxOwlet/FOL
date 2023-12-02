package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.exception.InvalidTypeSizeException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;

import java.util.Objects;

public abstract class WholeNumberType<T extends Number> implements TypeDescriptor {
    private final String folTypeName;

    protected WholeNumberType(String folTypeName) {
        this.folTypeName = folTypeName;
    }

    @Override
    public String name() {
        return folTypeName;
    }

    @Override
    public byte[] encode(Object value) {
        if (!(value instanceof Number)) {
            throw new IncompatibleTypeException(value, this);
        }
        long longVal = ReflectionUtils.as(value, Number.class).longValue();
        byte[] data = new byte[size()];
        for (int i = size() - 1; i >= 0; i--) {
            data[i] = (byte) (longVal & 0xff);
            longVal >>= 8;
        }
        return data;
    }

    @Override
    public Object decode(byte[] data) {
        if (data.length != size()) {
            throw new InvalidTypeSizeException(size(), data.length);
        }
        long longVal = 0;
        for (byte datum : data) {
            longVal <<= 8;
            longVal |= datum & 0xff;
        }
        return getValue(longVal);
    }

    protected abstract Object getValue(long longVal);

    @Override
    public boolean isCompatibleWith(TypeDescriptor other) {
        return equals(other);
    }

    // TODO: use singletons at least for predefined types?
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        WholeNumberType<?> that = (WholeNumberType<?>) object;
        return Objects.equals(folTypeName, that.folTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(folTypeName);
    }
}
