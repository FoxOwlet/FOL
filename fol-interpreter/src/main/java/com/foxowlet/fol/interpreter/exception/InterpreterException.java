package com.foxowlet.fol.interpreter.exception;

public abstract class InterpreterException extends RuntimeException {
    protected InterpreterException() {
        super();
    }

    protected InterpreterException(Throwable cause) {
        super(cause);
    }

    protected InterpreterException(String message) {
        super(message);
    }

    protected InterpreterException(String message, Throwable cause) {
        super(message, cause);
    }
}
