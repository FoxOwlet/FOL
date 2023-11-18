package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Subtraction;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.literal;
import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;

class SubtractionInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldSubtractTwoIntegers() {
        // 42 - 10
        Subtraction subtraction = new Subtraction(literal(42), literal(10));

        Object actual = interpret(subtraction);

        assertValue(actual).is(32);
    }

    @Test
    void shouldSubtractTwoLongs() {
        // 42L - 10L
        Subtraction subtraction = new Subtraction(literal(42L), literal(10L));

        Object actual = interpret(subtraction);

        assertValue(actual).is(32L);
    }

    @Test
    void shouldSubtractTwoBytes() {
        // 42b - 10b
        Subtraction subtraction = new Subtraction(literal((byte) 42), literal((byte) 10L));

        Object actual = interpret(subtraction);

        assertValue(actual).is((byte) 32);
    }

    @Test
    void shouldSupportNegativeResult() {
        // 10 - 42
        Subtraction subtraction = new Subtraction(literal(10), literal(42));

        Object actual = interpret(subtraction);

        assertValue(actual).is(-32);
    }

    @Test
    void shouldFail_whenIncompatibleTypes() {
        // 32 - 42L
        Subtraction subtraction = new Subtraction(literal(32), literal(42L));

        assertError(IncompatibleTypeException.class, subtraction);
    }
}