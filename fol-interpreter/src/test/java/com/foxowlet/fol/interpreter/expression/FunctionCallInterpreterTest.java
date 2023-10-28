package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.exception.InvalidFunctionCall;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AstUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class FunctionCallInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldCallFunctionFromVariable() {
        // val foo: Unit->Int = #(): Int { 42 }
        Assignment assignment = new Assignment(
                var("foo", ftype("Unit", "Int")),
                lambda(block(literal(42))));
        // foo()
        FunctionCall functionCall = call(new Symbol("foo"));
        // { ... }
        Block block = block(assignment, functionCall);

        Object actual = interpret(block);

        assertEquals(42, actual);
    }

    @Test
    void shouldCallFunction_whenCalledInplace() {
        // #(): Int { 42 }()
        FunctionCall functionCall = call(lambda(block(literal(42))));

        Object actual = interpret(functionCall);

        assertEquals(42, actual);
    }

    @Test
    void shouldCallFunction_whenHasSingleArgument() {
        // #(a: Int): Int { a }
        Lambda lambda = lambda(block(new Symbol("a")), formal("a", "Int"));
        // #(a: Int): Int { a }(42)
        FunctionCall functionCall = call(lambda, literal(42));

        Object actual = interpret(functionCall);

        assertEquals(42, actual);
    }

    @Test
    void shouldCallFunction_whenHasMultipleArguments() {
        // #(a: Int, b: Int): Int { a + b }
        Lambda lambda = lambda(block(new Addition(new Symbol("a"), new Symbol("b"))),
                formal("a", "Int"), formal("b", "Int"));
        // ...(40, 2)
        FunctionCall functionCall = call(lambda, literal(40), literal(2));

        Object actual = interpret(functionCall);

        assertEquals(42, actual);
    }

    @Test
    void shouldReturnValue_whenFunctionHasShadowVariable() {
        // #(a: Int): Int { var a: Int = a + 2; a }
        Lambda lambda = lambda(block(
                        var("a", "Int", new Addition(new Symbol("a"), literal(2))),
                        new Symbol("a")),
                formal("a", "Int"));
        // ...(40)
        FunctionCall functionCall = call(lambda, literal(40));

        Object actual = interpret(functionCall);

        assertEquals(42, actual);
    }

    @Test
    void shouldResolveVariable_whenDeclaredInCallArguments() {
        Lambda lambda = lambda(block(new Symbol("a")), formal("a", "Int"));
        Assignment assignment = var("a", "Int", literal(42));
        // {
        //   #(a: Int): Int { a }(var a: Int = 42)
        //   a
        // }
        Block block = block(call(lambda, assignment), new Symbol("a"));

        assertEquals(42, interpret(block));
    }

    @Test
    void shouldThrowException_whenIncompatibleArgumentTypes() {
        // #(a: Int): Int { a }
        Lambda lambda = lambda(block(new Symbol("a")), formal("a", "Int"));
        // ...(42L)
        FunctionCall call = call(lambda, literal(42L));

        assertError(IncompatibleTypeException.class, call);
    }

    @Test
    void shouldThrowException_whenIncompatibleReturnType() {
        // #(): Int { 42L }
        Lambda lambda = lambda(block(literal(42L)));
        // ...()
        FunctionCall call = call(lambda);

        assertError(IncompatibleTypeException.class, call);
    }

    @Test
    void shouldThrowException_whenInvalidArgumentsCount() {
        // #(): Int { 42 }
        Lambda lambda = lambda(block(literal(42)));
        // ...()
        FunctionCall call = call(lambda, literal(42));

        assertError(InvalidFunctionCall.class, call);
    }
}