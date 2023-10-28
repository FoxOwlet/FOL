package com.foxowlet.fol.interpreter.model.type;

public class ByteType extends WholeNumberType<Byte> {

    public ByteType() {
        super("Byte");
    }

    @Override
    public int size() {
        return Byte.BYTES;
    }

    @Override
    protected Object getValue(long longVal) {
        return (byte) longVal;
    }
}
