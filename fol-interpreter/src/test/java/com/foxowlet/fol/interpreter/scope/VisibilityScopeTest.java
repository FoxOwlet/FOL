package com.foxowlet.fol.interpreter.scope;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.UndefinedSymbolException;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisibilityScopeTest extends AbstractInterpreterTest {
    @Test
    void shouldResolveShadowVariables() {
        Block inner = block(var("a", "Int", literal(42)));
        // {
        //    var a: Int = 10
        //    { var a: Int = 42 }
        // }
        Block outer = block(
                var("a", "Int", literal(10)),
                inner);

        assertEquals(42, interpret(outer));
    }

    @Test
    void shouldAllowOuterVariableUsage_whenShadowing() {
        Block inner = block(
                var("a", "Int", new Addition(new Symbol("a"), literal(2))));
        // {
        //    var a: Int = 40
        //    { var a: Int = a + 2 }
        // }
        Block outer = block(
                var("a", "Int", literal(40)),
                inner);

        assertEquals(42, interpret(outer));
    }

    @Test
    void shouldThrowException_whenVariableUsedOutsideOfScope() {
        Block inner = block(var("a", "Int", literal(42)));
        // {
        //   { var a: Int = 42}
        //   a
        // }
        Block outer = block(inner, new Symbol("a"));

        assertError(UndefinedSymbolException.class, outer);
    }

    @Test
    void shouldThrowException_whenParameterUsedOutsideOfScope() {
        // #(a: Int){ a }
        Lambda lambda = lambda(block(new Symbol("a")), formal("a", "Int"));
        // #(a: Int){ a }(42)
        FunctionCall functionCall = call(lambda, literal(42));
        Symbol var = new Symbol("a");
        // {
        //   #(a: Int){ a }(42)
        //   a
        // }
        Block block = block(functionCall, var);

        assertError(UndefinedSymbolException.class, block);
    }

    @Test
    void shouldShadowParameters_whenSameOrder() {
        Lambda lambda = lambda(block(new Addition(new Symbol("a"), new Symbol("b"))),
                formal("a", "Int"),
                formal("b", "Int"));
        // {
        //   var a: Int = 40
        //   var b: Int = 2
        //   #(a: Int, b: Int){ a + b }(a, b)
        // }
        Block block = block(
                var("a", "Int", literal(40)),
                var("b", "Int", literal(2)),
                call(lambda, new Symbol("a"), new Symbol("b")));

        assertEquals(42, interpret(block));
    }

    @Test
    void shouldShadowParameters_whenReverseOrder() {
        Lambda lambda = lambda(block(new Addition(new Symbol("a"), new Symbol("b"))),
                formal("a", "Int"),
                formal("b", "Int"));
        // {
        //   var a: Int = 40
        //   var b: Int = 2
        //   #(a: Int, b: Int){ a + b }(b, a)
        // }
        Block block = block(
                var("a", "Int", literal(40)),
                var("b", "Int", literal(2)),
                call(lambda, new Symbol("b"), new Symbol("a")));

        assertEquals(42, interpret(block));
    }
}
