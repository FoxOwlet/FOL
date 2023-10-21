package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

public interface Value {
    Object value();

    TypeDescriptor type();

    MemoryLocation memory();
}
