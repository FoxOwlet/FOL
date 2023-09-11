package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.Function;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.type.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.foxowlet.fol.interpreter.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class FunctionDeclInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldCreateBoundSymbol() {
        Block body = block();
        // def foo(): Unit {}
        FunctionDecl foo = new FunctionDecl(new Symbol("foo"), List.of(), type("Unit"), body);

        Object actual = interpret(foo);

        assertValue(new Function(1, List.of(), body), actual);
    }

    @Test
    void shouldComputeProperType_whenNoArgs() {
        Block body = block(new IntLiteral(42));
        // def foo(): Int { 42 }
        FunctionDecl foo = new FunctionDecl(new Symbol("foo"), List.of(), type("Int"), body);

        Object actual = interpret(foo);

        Variable variable = assertInstanceOf(Variable.class, actual);
        assertFunctionType(variable, new UnitType(), new IntType());
    }

    @Test
    void shouldComputeProperType_whenMultipleArgs() {
        Block body = new Block(List.of());
        List<FormalParameter> params = List.of(
                new FormalParameter(new Symbol("a"), type("Long")),
                new FormalParameter(new Symbol("b"), type("Byte"))
        );
        // def foo(a: Long, b: Byte): Unit {}
        FunctionDecl foo = new FunctionDecl(new Symbol("foo"), params, type("Unit"), body);

        Object actual = interpret(foo);

        Variable variable = assertInstanceOf(Variable.class, actual);
        assertFunctionType(variable, new LongType(), new ByteType(), new UnitType());
    }
}