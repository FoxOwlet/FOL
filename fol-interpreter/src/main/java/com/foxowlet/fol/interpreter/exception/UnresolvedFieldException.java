package com.foxowlet.fol.interpreter.exception;

import java.util.function.Supplier;

public class UnresolvedFieldException extends InterpreterException {
    public UnresolvedFieldException(String field, String struct) {
        super(String.format("Can't resolve field '%s' in the context of struct '%s'", field, struct));
    }

    public static Supplier<UnresolvedFieldException> prepare(String field, String struct) {
        return () -> new UnresolvedFieldException(field, struct);
    }
}
