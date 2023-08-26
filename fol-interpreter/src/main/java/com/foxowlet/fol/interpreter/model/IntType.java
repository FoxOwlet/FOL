package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;

public class IntType implements Type {
    @Override
    public int size() {
        return 4;
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof Integer i) {
            // a * 256^3 + b * 256^2 + c * 256 + d
            byte[] data = new byte[4];
            for (int j = 3; j >= 0; j--) {
                data[j] = (byte) (i & 0xff);
                i >>= 8;
            }
            return data;
        }
        throw new IncompatibleTypeException(value, "integer");
    }

    @Override
    public Integer decode(byte[] data) {
        int value = 0;
        for (byte datum : data) {
            value <<= 8;
            value |= datum & 0xff;
        }
        return value;
    }
}
