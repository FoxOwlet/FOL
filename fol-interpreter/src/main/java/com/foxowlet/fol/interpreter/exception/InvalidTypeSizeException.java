package com.foxowlet.fol.interpreter.exception;

public class InvalidTypeSizeException extends IllegalStateException {
    public InvalidTypeSizeException(int expectedSize, int actualSize) {
        super("Invalid type size, expected %d, got %d bytes".formatted(expectedSize, actualSize));
    }
}
