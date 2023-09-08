package com.foxowlet.fol.interpreter.model.type;

public class IntType extends WholeNumberType<Integer> {
    private static final int SIZE_IN_BYTES = 4;
    private static final int SIZE_IN_BITS = SIZE_IN_BYTES * 8;
    private static final long BIT_MASK = (1L << SIZE_IN_BITS) - 1;

    public IntType() {
        super(Integer.class, "Int");
    }

    @Override
    public int size() {
        return SIZE_IN_BYTES;
    }

    @Override
    protected Object getValue(long valBits) {
        return (int) (valBits & BIT_MASK);
    }
}
