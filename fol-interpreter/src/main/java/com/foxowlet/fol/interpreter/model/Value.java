package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.interpreter.exception.TypeException;
import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.type.RefType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

public interface Value {
    Object value();

    default Object deref() {
        if (type() instanceof RefType refType) {
            return refType.deref(value());
        }
        throw new TypeException(value(), RefType.class);
    }

    TypeDescriptor type();

    MemoryLocation memory();
}
