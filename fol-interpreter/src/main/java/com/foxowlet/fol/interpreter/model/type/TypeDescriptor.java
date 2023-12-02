package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.model.memory.DummyMemoryLocation;
import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.Value;

public interface TypeDescriptor extends Value {
    String name();
    int size();
    byte[] encode(Object value);
    Object decode(byte[] data);

    default boolean isCompatibleWith(TypeDescriptor other) {
        return equals(other);
    }

    @Override
    default Object value() {
        return this;
    }

    @Override
    default TypeDescriptor type() {
        return new MetaType(this);
    }

    @Override
    default MemoryLocation memory() {
        return new DummyMemoryLocation();
    }
}
