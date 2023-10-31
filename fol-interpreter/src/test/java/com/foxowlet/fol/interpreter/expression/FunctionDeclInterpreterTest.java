package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.FunctionDecl;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.type.ByteType;
import com.foxowlet.fol.interpreter.model.type.IntType;
import com.foxowlet.fol.interpreter.model.type.LongType;
import com.foxowlet.fol.interpreter.model.type.UnitType;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertFunction;
import static com.foxowlet.fol.interpreter.AstUtils.*;

class FunctionDeclInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldCreateBoundSymbol() {
        Block body = block();
        // def foo(): Unit {}
        FunctionDecl foo = fdecl("foo", "Unit", body);

        Object actual = interpret(foo);

        assertFunction(actual)
                .hasBody(body)
                .hasNoParams()
                .hasReturnType(new UnitType());
    }

    @Test
    void shouldComputeProperType_whenNoArgs() {
        Block body = block(literal(42));
        // def foo(): Int { 42 }
        FunctionDecl foo = fdecl("foo", "Int", body);

        Object actual = interpret(foo);

        assertFunction(actual)
                .hasBody(body)
                .hasNoParams()
                .hasReturnType(new IntType());
    }

    @Test
    void shouldComputeProperType_whenMultipleArgs() {
        // def foo(a: Long, b: Byte): Unit {}
        FunctionDecl foo = fdecl("foo", "Unit", block(),
                formal("a", "Long"),
                formal("b", "Byte"));

        Object actual = interpret(foo);

        assertFunction(actual)
                .hasReturnType(new UnitType())
                .type()
                .withParam(new LongType())
                .withParam(new ByteType())
                .returns(new UnitType());
    }
}