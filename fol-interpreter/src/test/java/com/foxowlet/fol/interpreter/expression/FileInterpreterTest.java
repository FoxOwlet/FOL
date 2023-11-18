package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.File;
import com.foxowlet.fol.ast.NodeSeq;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.*;
import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;

class FileInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldReturnLastExpressionResult() {
        File file = new File(NodeSeq.of(literal(1), literal(2)));

        Object actual = interpret(file);

        assertValue(actual).is(2);
    }

    @Test
    void shouldNotCreateVisibilityScope() {
        // hack: this is not a valid AST
        // but let it exist to test visibility rules
        // variable declared in one file should be visible in another
        Block block = block(
                new File(NodeSeq.of(var("foo", "Int", literal(42)))),
                new File(NodeSeq.of(new Symbol("foo"))));

        Object actual = interpret(block);

        assertValue(actual).is(42);
    }
}