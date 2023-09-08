package com.foxowlet.fol.interpreter.model.type;

public interface Type {
    int size();
    byte[] encode(Object value);
    Object decode(byte[] data);
}
