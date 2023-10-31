package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertStruct;
import static com.foxowlet.fol.interpreter.AstUtils.*;

public class StructTest extends AbstractInterpreterTest {
    @Test
    void shouldCreateStructInstance() {
        // struct Foo(i: Int)
        StructDecl struct = new StructDecl(new Symbol("Foo"), NodeSeq.of(field("i", "Int")));
        // var f: Foo = Foo(42)
        Assignment expr = var("f", "Foo", call(new Symbol("Foo"), new IntLiteral(42)));
        // { ... }
        Block block = block(struct, expr);

        Object actual = interpret(block);

        assertStruct(actual)
                .hasField(42, new IntType());
    }

    @Test
    void shouldCreateStructInstance_whenMultipleFields() {
        // struct Foo(i: Int, j: Int)
        StructDecl struct = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                        field("i", "Int"),
                        field("j", "Int")));
        // var f: Foo = Foo(42, -99)
        Assignment expr = var("f", "Foo",
                call(new Symbol("Foo"),
                        new IntLiteral(42),
                        new IntLiteral(-99)));
        // { ... }
        Block block = block(struct, expr);

        Object actual = interpret(block);

        assertStruct(actual)
                .hasField(42, new IntType())
                .hasField(-99, new IntType());
    }

    @Test
    void shouldThrowException_whenIncompatibleFieldType() {
        // struct Foo(i: Int)
        StructDecl struct = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int")));
        // Foo(42L)
        FunctionCall call = call(new Symbol("Foo"), literal(42L));
        // { ... }
        Block block = block(struct, call);

        assertError(IncompatibleTypeException.class, block);
    }
}
