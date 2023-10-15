package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.NodeSeq;
import com.foxowlet.fol.ast.StructDecl;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.Field;
import com.foxowlet.fol.interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertStructType;
import static com.foxowlet.fol.interpreter.AstUtils.*;

class StructDeclInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldRegisterStructType() {
        // struct Foo(i: Int, j: Int, k: Int)
        StructDecl structDecl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int"),
                field("j", "Int"),
                field("k", "Int")));
        // Foo
        Symbol type = new Symbol("Foo");
        // { ... }
        Block block = block(structDecl, type);

        Object actual = interpret(block);

        assertStructType(actual, "Foo",
                new Field(0, "i", new IntType()),
                new Field(4, "j", new IntType()),
                new Field(8, "k", new IntType()));
    }

    @Test
    void shouldFail_whenDuplicateField() {
        // struct Foo(i: Int, i: Int)
        StructDecl structDecl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int"),
                field("i", "Int")));

        assertError(structDecl);
    }
}