package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Addition;
import com.foxowlet.fol.ast.Assignment;
import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.*;

class BlockInterpreterTest extends AbstractInterpreterTest {

    @Test
    void shouldReturnLastExpressionValue() {
        // var i: Int = 40 + 42
        Expression expr1 = new Assignment(
                var("i", "Int"),
                new Addition(literal(40), literal(42)));
        // var j: Int = i + 10;
        Expression expr2 = new Assignment(
                var("j", "Int"),
                new Addition(new Symbol("i"), literal(10)));
        // { ... }
        Expression block = block(expr1, expr2);

        Object actual = interpret(block);

        assertValue(actual).is(92);
    }
}
