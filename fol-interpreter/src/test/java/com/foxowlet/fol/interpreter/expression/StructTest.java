package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertStruct;
import static com.foxowlet.fol.interpreter.AstUtils.*;

public class StructTest extends AbstractInterpreterTest {
    @Test
    void shouldCreateStructInstance() {
        // struct Foo { var i: Int }
        StructDecl struct = new StructDecl(new Symbol("Foo"), block(var("i", "Int")));
        // var f: Foo = Foo(42)
        Assignment expr = var("f", "Foo", call(new Symbol("Foo"), new IntLiteral(42)));
        // { ... }
        Block block = block(struct, expr);

        Object actual = interpret(block);

        assertStruct(actual, new IntType().encode(42));
    }

    @Test
    void shouldCreateStructInstance_whenMultipleFields() {
        // struct Foo { var i: Int var j: Int }
        StructDecl struct = new StructDecl(new Symbol("Foo"), block(
                        var("i", "Int"),
                        var("j", "Int")));
        // var f: Foo = Foo(42, -99)
        Assignment expr = var("f", "Foo",
                call(new Symbol("Foo"),
                        new IntLiteral(42),
                        new IntLiteral(-99)));
        // { ... }
        Block block = block(struct, expr);

        Object actual = interpret(block);

        assertStruct(actual,
                new IntType().encode(42),
                new IntType().encode(-99));
    }
}
