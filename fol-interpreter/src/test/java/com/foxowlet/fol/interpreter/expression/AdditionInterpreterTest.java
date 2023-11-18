package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Addition;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.literal;

class AdditionInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldAddTwoIntegers() {
        // 32 + 10
        Addition addition = new Addition(literal(32), literal(10));

        Object actual = interpret(addition);

        assertValue(actual).is(42);
    }

    @Test
    void shouldAddTwoLongs() {
        // 32L + 10L
        Addition addition = new Addition(literal(32L), literal(10L));

        Object actual = interpret(addition);

        assertValue(actual).is(42L);
    }

    @Test
    void shouldAddTwoBytes() {
        // 32b + 10b
        Addition addition = new Addition(literal((byte) 32), literal((byte) 10L));

        Object actual = interpret(addition);

        assertValue(actual).is((byte) 42);
    }

    @Test
    void shouldFail_whenIncompatibleTypes() {
        // 32 + 42L
        Addition addition = new Addition(literal(32), literal(42L));

        assertError(IncompatibleTypeException.class, addition);
    }
}