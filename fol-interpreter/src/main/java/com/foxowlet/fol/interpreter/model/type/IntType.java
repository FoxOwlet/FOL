package com.foxowlet.fol.interpreter.model.type;

public class IntType extends WholeNumberType<Integer> {

    public IntType() {
        super(Integer.class, "Int");
    }

    @Override
    public int size() {
        return Integer.BYTES;
    }

    @Override
    protected Object getValue(long valBits) {
        return (int) valBits;
    }
}
