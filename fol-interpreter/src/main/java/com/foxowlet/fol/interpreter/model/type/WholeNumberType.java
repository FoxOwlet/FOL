package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;

public abstract class WholeNumberType<T extends Number> implements Type {
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
        int size = size();
        long valBits = getBits(value, size);
        byte[] data = new byte[size];
        for (int i = size - 1; i >= 0; i--) {
            data[i] = (byte) (valBits & 0xff);
            valBits >>= 8;
        }
        return data;
    }

    @Override
    public Object decode(byte[] data) {
        long valBits = 0;
        for (byte datum : data) {
            valBits <<= 8;
            valBits |= datum & 0xff;
        }
        return getValue(valBits);
    }

    private long getBits(Object value, int size) {
        long longVal = javaClass.cast(value).longValue();
        if (size == Long.BYTES) {
            return longVal;
        }
        long mask = (1L << size * 8) - 1; // <size> bytes all filled with "1"
        return longVal & mask;
    }

    protected abstract Object getValue(long valBits);
}
