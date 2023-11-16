package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Multiplication;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.literal;
import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;

class MultiplicationInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldMultiplyTwoIntegers() {
        // 42 * 10
        Multiplication multiplication = new Multiplication(literal(42), literal(10));

        Object actual = interpret(multiplication);

        assertValue(actual).is(420);
    }

    @Test
    void shouldMultiplyTwoLongs() {
        // 42L + 10L
        Multiplication multiplication = new Multiplication(literal(42L), literal(10L));

        Object actual = interpret(multiplication);

        assertValue(actual).is(420L);
    }

    @Test
    void shouldMultiplyTwoBytes() {
        // 3b + 10b
        Multiplication multiplication = new Multiplication(literal((byte) 3), literal((byte) 10L));

        Object actual = interpret(multiplication);

        assertValue(actual).is((byte) 30);
    }

    @Test
    void shouldFail_whenIncompatibleTypes() {
        // 32 * 42L
        Multiplication multiplication = new Multiplication(literal(32), literal(42L));

        assertError(IncompatibleTypeException.class, multiplication);
    }
}