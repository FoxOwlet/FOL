package com.foxowlet.fol.interpreter.exception;

public class IncompatibleTypeException extends InterpreterException {
    public IncompatibleTypeException(Object object, String type) {
        super(String.format("Type %s of %s is incompatible with %s",
                object.getClass().getName(), object, type));
    }
}
