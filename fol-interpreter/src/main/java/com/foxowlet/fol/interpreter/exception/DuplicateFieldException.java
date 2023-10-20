package com.foxowlet.fol.interpreter.exception;

public class DuplicateFieldException extends InterpreterException {
    public DuplicateFieldException(String field, String struct) {
        super("Field '%s' is already defined in the context of struct '%s'"
                .formatted(field, struct));
    }
}
