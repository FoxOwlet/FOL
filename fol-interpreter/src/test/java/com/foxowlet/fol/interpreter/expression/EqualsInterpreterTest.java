package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Equals;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.model.type.BooleanType;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.literal;
import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;

class EqualsInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldReturnBoolean() {
        Equals equals = new Equals(literal(10), literal(10));

        Object actual = interpret(equals);

        assertValue(actual).hasType(new BooleanType());
    }

    @Test
    void shouldReturnTrue_whenEqualValues() {
        Equals equals = new Equals(literal(10), literal(10));

        Object actual = interpret(equals);

        assertValue(actual).is(true);
    }

    @Test
    void shouldReturnTrue_whenDifferentValues() {
        Equals equals = new Equals(literal(42), literal(10));

        Object actual = interpret(equals);

        assertValue(actual).is(false);
    }

    @Test
    void shouldThrowIncompatibleTypeException_whenInvalidTypes() {
        Equals equals = new Equals(literal(42L), literal(10));

        assertError(IncompatibleTypeException.class, equals);
    }
}