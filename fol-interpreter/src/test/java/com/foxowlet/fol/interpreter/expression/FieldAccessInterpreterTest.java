package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertStruct;
import static com.foxowlet.fol.interpreter.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.*;

class FieldAccessInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldReturnFieldValue_whenReadContext() {
        // struct Foo(i: Int, j: Int)
        StructDecl decl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int"),
                field("j", "Int")));
        // var foo: Foo = Foo(42, 0)
        Assignment var = var("foo", "Foo",
                call(new Symbol("Foo"), literal(42), literal(0)));
        // foo.i
        FieldAccess field = fieldAccess("foo","i");
        // { ... }
        Block block = block(decl, var, field);

        Object actual = interpret(block);

        assertValue(42, actual);
    }

    @Test
    void shouldSetFieldValue_whenWriteContext() {
        // struct Foo(i: Int, j: Int)
        StructDecl decl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int"),
                field("j", "Int")));
        // var foo: Foo = Foo(42, 0)
        Assignment var = var("foo", "Foo",
                call(new Symbol("Foo"), literal(42), literal(0)));
        // foo.i = 99
        Assignment assignment = new Assignment(
                fieldAccess("foo","i"),
                literal(99));
        // foo.i
        FieldAccess field = fieldAccess("foo","i");
        // { ... }
        Block block = block(decl, var, assignment, field);

        Object actual = interpret(block);

        assertValue(99, actual);
    }

    @Test
    void shouldUpdateStructMemory_whenFieldSet() {
        // struct Foo(i: Int, j: Int)
        StructDecl decl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int"),
                field("j", "Int")));
        // var foo: Foo = Foo(42, 0)
        Assignment var = var("foo", "Foo",
                call(new Symbol("Foo"), literal(42), literal(0)));
        // foo.j = 99
        Assignment assignment = new Assignment(
                fieldAccess("foo","j"),
                literal(99));
        // foo
        Symbol foo = new Symbol("foo");
        // { ... }
        Block block = block(decl, var, assignment, foo);

        Object actual = interpret(block);

        assertStruct(actual,
                new IntType().encode(42),
                new IntType().encode(99));
    }

    @Test
    void shouldReturnFieldValue_whenInplaceReadContext() {
        // struct Foo(i: Int, j: Int)
        StructDecl decl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int"),
                field("j", "Int")));
        // Foo(42, 0).i
        FieldAccess field = fieldAccess(
                call(new Symbol("Foo"), literal(42), literal(0)),
                "i");
        // { ... }
        Block block = block(decl, field);

        Object actual = interpret(block);

        assertValue(42, actual);
    }

    @Test
    void shouldResolveAddress_whenDifferentStructInstanceUsed() {
        // struct Foo(i: Int, j: Int)
        StructDecl decl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int"),
                field("j", "Int")));
        // var f1: Foo = Foo(42, 99)
        Assignment f1 = var("f1", "Foo",
                call(new Symbol("Foo"), literal(42), literal(99)));
        // var f2: Foo = Foo(10, 11)
        Assignment f2 = var("f2", "Foo",
                call(new Symbol("Foo"), literal(10), literal(11)));
        // f1.i + f2.i
        Addition addition = new Addition(
                fieldAccess("f1","i"),
                fieldAccess("f2","i"));
        // { ... }
        Block block = block(decl, f1, f2, addition);

        Object actual = interpret(block);

        assertValue(52, actual);
    }
}