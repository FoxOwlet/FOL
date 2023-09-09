package com.foxowlet.fol.interpreter.model.type;

public interface TypeDescriptor {
    String name();
    int size();
    byte[] encode(Object value);
    Object decode(byte[] data);
}
