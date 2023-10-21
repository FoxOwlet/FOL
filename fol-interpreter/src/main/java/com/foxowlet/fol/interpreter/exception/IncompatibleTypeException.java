package com.foxowlet.fol.interpreter.exception;

import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

public class IncompatibleTypeException extends InterpreterException {
    public IncompatibleTypeException(Object object, TypeDescriptor type) {
       this(object.getClass().getName(), object, type.name());
    }

    public IncompatibleTypeException(Value source, TypeDescriptor target) {
        this(source.type().name(), source.value(), target.name());
    }

    private IncompatibleTypeException(String sourceType, Object source, String targetType) {

        super(String.format("Type %s of %s is incompatible with %s", sourceType, source, targetType));
    }
}
