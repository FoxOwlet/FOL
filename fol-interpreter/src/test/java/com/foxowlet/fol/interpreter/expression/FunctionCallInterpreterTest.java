package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.*;


class FunctionCallInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldCallFunctionFromVariable() {
        // val foo: Unit->Int = #(){ 42 }
        Assignment assignment = new Assignment(
                var("foo", ftype("Unit", "Int")),
                lambda(block(literal(42))));
        // foo()
        FunctionCall functionCall = call(new Symbol("foo"));
        // { ... }
        Block block = block(assignment, functionCall);

        Object actual = interpret(block);

        assertValue(42, actual);
    }

    @Test
    void shouldCallFunction_whenCalledInplace() {
        // #(){ 42 }()
        FunctionCall functionCall = call(lambda(block(literal(42))));

        Object actual = interpret(functionCall);

        assertValue(42, actual);
    }

    @Test
    void shouldCallFunction_whenHasSingleArgument() {
        // #(a: Int){ a }
        Lambda lambda = lambda(block(new Symbol("a")), formal("a", "Int"));
        // #(a: Int){ a }(42)
        FunctionCall functionCall = call(lambda, literal(42));

        Object actual = interpret(functionCall);

        assertValue(42, actual);
    }

    @Test
    void shouldCallFunction_whenHasMultipleArguments() {
        // #(a: Int, b: Int){ a + b }
        Lambda lambda = lambda(block(new Addition(new Symbol("a"), new Symbol("b"))),
                formal("a", "Int"), formal("b", "Int"));
        // ...(40, 2)
        FunctionCall functionCall = call(lambda, literal(40), literal(2));

        Object actual = interpret(functionCall);

        assertValue(42, actual);
    }

    @Test
    void shouldReturnValue_whenFunctionHasShadowVariable() {
        // #(a: Int){ var a: Int = a + 2; a }
        Lambda lambda = lambda(block(new Assignment(
                                var("a", "Int"),
                                new Addition(new Symbol("a"), literal(2))),
                        new Symbol("a")),
                formal("a", "Int"));
        // ...(40)
        FunctionCall functionCall = call(lambda, literal(40));

        Object actual = interpret(functionCall);

        assertValue(42, actual);
    }

    @Test
    void shouldResolveVariable_whenDeclaredInCallArguments() {
        Lambda lambda = lambda(block(), formal("a", "Int"));
        Assignment assignment = var("a", "Int", literal(42));
        // {
        //   #(a: Int){ }(var a: Int = 42)
        //   a
        // }
        Block block = block(call(lambda, assignment), new Symbol("a"));

        assertValue(42, interpret(block));
    }
}