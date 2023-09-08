package com.foxowlet.fol.interpreter.model.type;

class ByteTypeTest extends WholeNumberTypeTest<Byte> {

    @Override
    protected WholeNumberType<Byte> type() {
        return new ByteType();
    }

    @Override
    protected Byte value() {
        return (byte) rnd.nextInt();
    }
}