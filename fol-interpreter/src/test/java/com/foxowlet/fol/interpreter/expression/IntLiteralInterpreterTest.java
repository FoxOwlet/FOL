package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.literal;

class IntLiteralInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldBeConvertedIntoValue() {
        Expression literal = literal(42);

        Object actual = interpret(literal);

        assertValue(actual).is(42);
    }
}
