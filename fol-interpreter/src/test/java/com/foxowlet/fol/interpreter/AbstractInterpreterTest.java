package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.interpreter.model.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AbstractInterpreterTest {
    private final Interpreter interpreter;

    public AbstractInterpreterTest() {
        this.interpreter = new Interpreter(new Emulator(), getInterpreterConfiguration());
    }

    protected InterpreterConfiguration getInterpreterConfiguration() {
        return new InterpreterConfiguration();
    }

    protected final Object interpret(Expression expression) {
        return interpreter.interpret(expression);
    }

    protected static <T> void assertValue(T expected, Object value) {
        assertInstanceOf(Value.class, value);
        assertEquals(expected, ((Value) value).value());
    }
}
