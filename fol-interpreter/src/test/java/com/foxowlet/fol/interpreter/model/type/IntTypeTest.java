package com.foxowlet.fol.interpreter.model.type;

class IntTypeTest extends WholeNumberTypeTest<Integer> {

    @Override
    protected WholeNumberType<Integer> type() {
        return new IntType();
    }

    @Override
    protected Integer value() {
        return rnd.nextInt();
    }
}