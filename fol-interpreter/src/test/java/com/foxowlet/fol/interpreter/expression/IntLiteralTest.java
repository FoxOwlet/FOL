package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.*;

class IntLiteralTest extends AbstractInterpreterTest {
    @Test
    void shouldBeConvertedIntoValue() {
        Expression literal = literal(42);

        Object actual = interpret(literal);

        assertValue(42, actual);
    }
}
