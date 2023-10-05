package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.model.Value;

public interface TypeDescriptor extends Value {
    String name();
    int size();
    byte[] encode(Object value);
    Object decode(byte[] data);

    @Override
    default Object value() {
        return this;
    }
}
