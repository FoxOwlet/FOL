package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.interpreter.exception.InterpreterException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractInterpreterTest {
    private final Interpreter interpreter;

    public AbstractInterpreterTest() {
        this.interpreter = new Interpreter(new Emulator(), configure(new InterpreterConfiguration()));
    }

    protected InterpreterConfiguration configure(InterpreterConfiguration configuration) {
        return configuration;
    }

    protected final Object interpret(Expression expression) {
        return interpreter.interpret(expression);
    }

    protected void assertError(Expression expression) {
        assertError(InterpreterException.class, expression);
    }

    protected <T extends InterpreterException> void assertError(Class<T> errorClass, Expression expression) {
        assertThrows(errorClass, () -> interpret(expression));
    }
}
