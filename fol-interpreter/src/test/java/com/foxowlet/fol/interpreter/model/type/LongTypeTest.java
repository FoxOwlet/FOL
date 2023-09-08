package com.foxowlet.fol.interpreter.model.type;

class LongTypeTest extends WholeNumberTypeTest<Long> {

    @Override
    protected WholeNumberType<Long> type() {
        return new LongType();
    }

    @Override
    protected Long value() {
        return rnd.nextLong();
    }
}