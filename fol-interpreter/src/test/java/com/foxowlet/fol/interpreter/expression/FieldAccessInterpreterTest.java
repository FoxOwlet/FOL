package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.UnresolvedFieldException;
import com.foxowlet.fol.interpreter.model.type.IntType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertStruct;
import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;
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

        assertValue(actual).is(42);
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

        assertValue(actual).is(99);
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

        assertStruct(actual)
                .hasField(42, new IntType())
                .hasField(99, new IntType());
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

        assertValue(actual).is(42);
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

        assertValue(actual).is(52);
    }

    @Test
    void shouldFail_whenUnresolvedField() {
        // struct Foo(i: Int)
        StructDecl decl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int")));
        // var foo: Foo = Foo(42)
        Assignment var = var("foo", "Foo",
                call(new Symbol("Foo"), literal(42)));
        // foo.j
        FieldAccess field = fieldAccess("foo", "j");
        // { ... }
        Block block = block(decl, var, field);

        assertError(UnresolvedFieldException.class, block);
    }

    @Test
    @Disabled("pass-by-value semantic is now deprecated")
    void shouldReadOldValue_whenPassedIntoFunctionByValue() {
        // struct Foo(i: Int)
        StructDecl decl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int")));
        // var foo: Foo = Foo(10)
        Assignment var = var("foo", "Foo",
                call(new Symbol("Foo"), literal(10)));
        // def bar(foo: Foo): Int { foo.i = 42 }
        FunctionDecl bar = fdecl("bar", "Int",
                block(new Assignment(
                        fieldAccess("foo", "i"),
                        literal(42))),
                formal("foo", "Foo"));
        // bar(foo)
        FunctionCall call = call(new Symbol("bar"), new Symbol("foo"));
        // foo.i
        FieldAccess field = fieldAccess("foo", "i");
        // { ... }
        Block block = block(decl, var, bar, call, field);

        Object actual = interpret(block);

        assertValue(actual).is(10);
    }

    @Test
    void shouldUpdateOldValue_whenPassedIntoFunctionByReference() {
        // struct Foo(i: Int)
        StructDecl decl = new StructDecl(new Symbol("Foo"), NodeSeq.of(
                field("i", "Int")));
        // var i: Int = 99
        Assignment i = var("i", "Int", literal(99));
        // var foo: Foo = Foo(10)
        Assignment foo = var("foo", "Foo",
                call(new Symbol("Foo"), literal(10)));
        // def bar(foo: Foo): Int { foo.i = 42 }
        FunctionDecl bar = fdecl("bar", "Int",
                block(new Assignment(
                        fieldAccess("foo", "i"),
                        literal(42))),
                formal("foo", "Foo"));
        // bar(foo)
        FunctionCall call = call(new Symbol("bar"), new Symbol("foo"));
        // foo.i
        FieldAccess field = fieldAccess("foo", "i");
        // { ... }
        Block block = block(decl, i, foo, bar, call, field);

        Object actual = interpret(block);

        assertValue(actual).is(42);
    }
}