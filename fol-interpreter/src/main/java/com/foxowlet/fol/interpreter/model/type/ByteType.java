package com.foxowlet.fol.interpreter.model.type;

public class ByteType extends WholeNumberType<Byte> {
    private static final int SIZE_IN_BYTES = 1;
    private static final int SIZE_IN_BITS = SIZE_IN_BYTES * 8;
    private static final long BIT_MASK = (1L << SIZE_IN_BITS) - 1;

    public ByteType() {
        super(Byte.class, "Byte");
    }

    @Override
    public int size() {
        return SIZE_IN_BYTES;
    }

    @Override
    protected Object getValue(long valBits) {
        return (byte) (valBits & BIT_MASK);
    }
}
