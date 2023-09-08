package com.foxowlet.fol.interpreter.exception;

import java.util.function.BiFunction;

public class TypeException extends InterpreterException {
    public TypeException(Object object, Class<?> type) {
        this("%s can't be interpreted as %s", object, type);
    }

    public TypeException(String format, Object object, Class<?> type) {
        super(String.format(format, object, type.getName()));
    }

    public static BiFunction<Object, Class<?>, InterpreterException> prepare(String message) {
        return (object, type) -> new TypeException(message, object, type);
    }
}
