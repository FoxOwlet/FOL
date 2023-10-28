package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IntLiteralInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldBeConvertedIntoValue() {
        Expression literal = literal(42);

        Object actual = interpret(literal);

        assertEquals(42, actual);
    }
}
