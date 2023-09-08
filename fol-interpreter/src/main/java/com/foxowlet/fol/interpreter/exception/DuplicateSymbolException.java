package com.foxowlet.fol.interpreter.exception;

import java.util.function.Supplier;

public class DuplicateSymbolException extends InterpreterException {
    public DuplicateSymbolException(String name) {
        super(String.format("Symbol '%s' is already defined in this context", name));
    }

    public static Supplier<DuplicateSymbolException> prepare(String name) {
        return () -> new DuplicateSymbolException(name);
    }
}
