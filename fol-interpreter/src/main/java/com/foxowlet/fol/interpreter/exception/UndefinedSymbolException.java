package com.foxowlet.fol.interpreter.exception;

import java.util.function.Supplier;

public class UndefinedSymbolException extends InterpreterException {
    public UndefinedSymbolException(String name) {
        super(String.format("Symbol '%s' is not defined in this context", name));
    }

    public static Supplier<UndefinedSymbolException> prepare(String name) {
        return () -> new UndefinedSymbolException(name);
    }
}
