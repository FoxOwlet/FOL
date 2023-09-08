package com.foxowlet.fol.interpreter.model.type;

public class LongType extends WholeNumberType<Long> {
    private static final int SIZE_IN_BYTES = 8;

    public LongType() {
        super(Long.class, "Long");
    }

    @Override
    public int size() {
        return SIZE_IN_BYTES;
    }

    @Override
    protected Object getValue(long valBits) {
        return valBits;
    }
}
