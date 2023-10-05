package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.StructDecl;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.FieldDecl;
import com.foxowlet.fol.interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertStructType;
import static com.foxowlet.fol.interpreter.AstUtils.block;
import static com.foxowlet.fol.interpreter.AstUtils.var;

class StructDeclInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldRegisterStructType() {
        StructDecl structDecl = new StructDecl(new Symbol("Foo"), block(
                var("i", "Int"),
                var("j", "Int"),
                var("k", "Int")));
        Block block = block(structDecl, new Symbol("Foo"));

        Object actual = interpret(block);

        assertStructType(actual, "Foo",
                new FieldDecl(0, "i", new IntType()),
                new FieldDecl(4, "j", new IntType()),
                new FieldDecl(8, "k", new IntType()));
    }

}