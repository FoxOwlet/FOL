package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.StructDecl;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.FieldDecl;
import com.foxowlet.fol.interpreter.model.type.IntType;
import com.foxowlet.fol.interpreter.model.type.StructType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.foxowlet.fol.interpreter.AstUtils.block;
import static com.foxowlet.fol.interpreter.AstUtils.var;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class StructDeclInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldRegisterStructType() {
        StructDecl structDecl = new StructDecl(new Symbol("foo"), block(
                var("i", "Int"),
                var("j", "Int"),
                var("k", "Int")));
        Block block = block(structDecl, new Symbol("foo"));

        Object actual = interpret(block);

        StructType type = assertInstanceOf(StructType.class, actual);
        assertEquals("foo", type.name());
        List<FieldDecl> expectedFields = List.of(
                new FieldDecl(0, "i", new IntType()),
                new FieldDecl(4, "j", new IntType()),
                new FieldDecl(8, "k", new IntType())
        );
        assertEquals(expectedFields, type.fields());
    }

}