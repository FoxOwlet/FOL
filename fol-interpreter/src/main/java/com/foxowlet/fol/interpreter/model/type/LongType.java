package com.foxowlet.fol.interpreter.model.type;

public class LongType extends WholeNumberType<Long> {

    public LongType() {
        super("Long");
    }

    @Override
    public int size() {
        return Long.BYTES;
    }

    @Override
    protected Object getValue(long longVal) {
        return longVal;
    }
}
