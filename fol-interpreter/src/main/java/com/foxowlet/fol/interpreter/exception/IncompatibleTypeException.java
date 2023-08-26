package com.foxowlet.fol.interpreter.exception;

public class IncompatibleTypeException extends RuntimeException {
    public IncompatibleTypeException(Object object, String type) {
        super("Type %s of %s is incompatible with %s".formatted(object.getClass().getName(), object, type));
    }
}
