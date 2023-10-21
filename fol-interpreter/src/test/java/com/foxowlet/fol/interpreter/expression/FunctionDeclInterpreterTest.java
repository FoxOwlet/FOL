package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.FunctionDecl;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.type.ByteType;
import com.foxowlet.fol.interpreter.model.type.IntType;
import com.foxowlet.fol.interpreter.model.type.LongType;
import com.foxowlet.fol.interpreter.model.type.UnitType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertFunction;
import static com.foxowlet.fol.interpreter.AssertionUtils.assertFunctionType;
import static com.foxowlet.fol.interpreter.AstUtils.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class FunctionDeclInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldCreateBoundSymbol() {
        Block body = block();
        // def foo(): Unit {}
        FunctionDecl foo = fdecl("foo", "Unit", body);

        Object actual = interpret(foo);

        assertFunction(List.of(), new UnitType(), body, actual);
    }

    @Test
    void shouldComputeProperType_whenNoArgs() {
        Block body = block(literal(42));
        // def foo(): Int { 42 }
        FunctionDecl foo = fdecl("foo", "Int", body);

        Object actual = interpret(foo);

        Variable variable = assertInstanceOf(Variable.class, actual);
        assertFunctionType(variable, new UnitType(), new IntType());
    }

    @Test
    void shouldComputeProperType_whenMultipleArgs() {
        // def foo(a: Long, b: Byte): Unit {}
        FunctionDecl foo = fdecl("foo", "Unit", block(),
                formal("a", "Long"),
                formal("b", "Byte"));

        Object actual = interpret(foo);

        Variable variable = assertInstanceOf(Variable.class, actual);
        assertFunctionType(variable, new LongType(), new ByteType(), new UnitType());
    }
}