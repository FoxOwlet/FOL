package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.VarDecl;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.DuplicateSymbolException;
import com.foxowlet.fol.interpreter.exception.UndefinedSymbolException;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.block;
import static com.foxowlet.fol.interpreter.AstUtils.var;

class VarDeclInterpreterTest extends AbstractInterpreterTest {

    @Test
    void shouldAllocateVariable() {
        // var foo: Long
        Expression varDecl = var("foo", "Long");

        Object actual = interpret(varDecl);

        assertValue(actual).is(0L);
    }

    @Test
    void shouldThrowException_whenTypeIsUnresolved() {
        // var foo: bar
        VarDecl varDecl = var("foo", "bar");

        assertError(UndefinedSymbolException.class, varDecl);
    }

    @Test
    void shouldThrowException_whenDuplicateSymbol() {
        // var i: Int
        VarDecl varDecl1 = var("i", "Int");
        // var i: Int
        VarDecl varDecl2 = var("i", "Int");
        // { ... }
        Block block = block(varDecl1, varDecl2);

        assertError(DuplicateSymbolException.class, block);
    }
}
