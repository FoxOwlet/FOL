package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Division;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.literal;
import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;

class DivisionInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldDivideTwoIntegers() {
        // 42 / 10
        Division division = new Division(literal(42), literal(10));

        Object actual = interpret(division);

        assertValue(actual).is(4);
    }

    @Test
    void shouldDivideTwoLongs() {
        // 42L / 10L
        Division division = new Division(literal(42L), literal(10L));

        Object actual = interpret(division);

        assertValue(actual).is(4L);
    }

    @Test
    void shouldDivideTwoBytes() {
        // 42b / 10b
        Division division = new Division(literal((byte) 42), literal((byte) 10L));

        Object actual = interpret(division);

        assertValue(actual).is((byte) 4);
    }

    @Test
    void shouldFail_whenIncompatibleTypes() {
        // 32 / 42L
        Division division = new Division(literal(32), literal(42L));

        assertError(IncompatibleTypeException.class, division);
    }
}