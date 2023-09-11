package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.IntLiteral;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.TestUtils.assertValue;

class IntLiteralTest extends AbstractInterpreterTest {
    @Test
    void shouldBeConvertedIntoValue() {
        Expression literal = new IntLiteral(42);

        Object actual = interpret(literal);

        assertValue(42, actual);
    }
}
