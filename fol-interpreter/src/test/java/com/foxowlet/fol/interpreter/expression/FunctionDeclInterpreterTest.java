package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.Function;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.type.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class FunctionDeclInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldCreateBoundSymbol() {
        Block body = new Block(List.of());
        ScalarType returnType = new ScalarType(new Symbol("Unit"));
        // def foo(): Unit {}
        FunctionDecl foo = new FunctionDecl(new Symbol("foo"), List.of(), returnType, body);

        Object actual = interpret(foo);

        assertValue(new Function(1, List.of(), body), actual);
    }

    @Test
    void shouldComputeProperType_whenNoArgs() {
        Block body = new Block(List.of(new IntLiteral(42)));
        ScalarType returnType = new ScalarType(new Symbol("Int"));
        // def foo(): Int { 42 }
        FunctionDecl foo = new FunctionDecl(new Symbol("foo"), List.of(), returnType, body);

        Object actual = interpret(foo);

        Variable variable = assertInstanceOf(Variable.class, actual);
        assertFunctionType(variable, new UnitType(), new IntType());
    }

    @Test
    void shouldComputeProperType_whenMultipleArgs() {
        Block body = new Block(List.of());
        ScalarType returnType = new ScalarType(new Symbol("Unit"));
        List<FormalParameter> params = List.of(
                new FormalParameter(new Symbol("a"), new ScalarType(new Symbol("Long"))),
                new FormalParameter(new Symbol("b"), new ScalarType(new Symbol("Byte")))
        );
        // def foo(a: Long, b: Byte): Unit {}
        FunctionDecl foo = new FunctionDecl(new Symbol("foo"), params, returnType, body);

        Object actual = interpret(foo);

        Variable variable = assertInstanceOf(Variable.class, actual);
        assertFunctionType(variable, new LongType(), new ByteType(), new UnitType());
    }
}