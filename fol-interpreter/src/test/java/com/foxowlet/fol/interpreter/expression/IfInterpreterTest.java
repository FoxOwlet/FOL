package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Equals;
import com.foxowlet.fol.ast.If;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.literal;
import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;

class IfInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldInterpretThenBranch_whenConditionIsTrue() {
        If expr = new If(new Equals(literal(1), literal(1)),
                literal(2),
                literal(3));

        Object actual = interpret(expr);

        assertValue(actual).is(2);
    }

    @Test
    void shouldInterpretElseBranch_whenConditionIsFalse() {
        If expr = new If(new Equals(literal(1), literal(0)),
                literal(2),
                literal(3));

        Object actual = interpret(expr);

        assertValue(actual).is(3);
    }
}