package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.exception.InvalidTypeSizeException;

public abstract class WholeNumberType<T extends Number> implements TypeDescriptor {
    private final Class<T> javaClass;
    private final String folTypeName;

    protected WholeNumberType(Class<T> javaClass, String folTypeName) {
        this.javaClass = javaClass;
        this.folTypeName = folTypeName;
    }

    @Override
    public String name() {
        return folTypeName;
    }

    @Override
    public byte[] encode(Object value) {
        if (!javaClass.isInstance(value)) {
            throw new IncompatibleTypeException(value, folTypeName);
        }
        long longVal = javaClass.cast(value).longValue();
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
}
