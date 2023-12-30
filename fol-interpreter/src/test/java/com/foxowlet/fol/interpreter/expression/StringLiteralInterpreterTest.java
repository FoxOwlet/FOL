package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.StringLiteral;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.*;
import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertString;

class StringLiteralInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldInterpretStringLiteral() {
        Object actual = interpret(new StringLiteral("abc"));

        assertString(actual)
                .is("abc");
    }

    @Test
    void stringLiteral_shouldBeAssignableIntoVariable() {
        // var s: String = "abc"
        Expression expr = var("s", "String", literal("abc"));

        Object actual = interpret(expr);

        assertString(actual)
                .is("abc");
    }

    @Test
    void stringLiteral_shouldBeAssignableBetweenVariables() {
        // {
        //   var s1: String = "abc"
        //   var s2: String = s1
        // }
        Expression expr = block(
                var("s1", "String", literal("abc")),
                var("s2", "String", new Symbol("s1")));

        Object actual = interpret(expr);

        assertString(actual)
                .is("abc");
    }
}