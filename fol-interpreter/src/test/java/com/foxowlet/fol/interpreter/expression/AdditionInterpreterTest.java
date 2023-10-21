package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Addition;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.literal;

class AdditionInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldAddTwoIntegers() {
        // 42 + 10
        Addition addition = new Addition(literal(32), literal(10));

        Object actual = interpret(addition);

        assertValue(42, actual);
    }

    @Test
    void shouldAddTwoLongs() {
        // 32L + 10L
        Addition addition = new Addition(literal(32L), literal(10L));

        Object actual = interpret(addition);

        assertValue(42L, actual);
    }

    @Test
    void shouldAddTwoBytes() {
        // 32b + 32b
        Addition addition = new Addition(literal((byte) 32), literal((byte) 10L));

        Object actual = interpret(addition);

        assertValue((byte) 42, actual);
    }

    @Test
    void shouldFail_whenIncompatibleTypes() {
        // 32 + 42L
        Addition addition = new Addition(literal(32), literal(42L));

        assertError(IncompatibleTypeException.class, addition);
    }
}