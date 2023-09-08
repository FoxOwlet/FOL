package com.foxowlet.fol.interpreter.model.type;

public class ByteType extends WholeNumberType<Byte> {

    public ByteType() {
        super(Byte.class, "Byte");
    }

    @Override
    public int size() {
        return Byte.BYTES;
    }

    @Override
    protected Object getValue(long valBits) {
        return (byte) valBits;
    }
}
