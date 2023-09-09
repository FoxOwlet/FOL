package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import java.util.List;

class BlockTest extends AbstractInterpreterTest {

    @Test
    void shouldReturnLastExpressionValue() {
        // var i: Int = 40 + 42
        Expression expr1 = new Assignment(
                new VarDecl(new Symbol("i"), new ScalarType(new Symbol("Int"))),
                new Addition(new IntLiteral(40), new IntLiteral(42)));
        // var j: Int = i + 10;
        Expression expr2 = new Assignment(
                new VarDecl(new Symbol("j"), new ScalarType(new Symbol("Int"))),
                new Addition(new Symbol("i"), new IntLiteral(10)));
        // { ... }
        Expression block = new Block(List.of(expr1, expr2));

        Object actual = interpret(block);

        assertValue(92, actual);
    }
}
