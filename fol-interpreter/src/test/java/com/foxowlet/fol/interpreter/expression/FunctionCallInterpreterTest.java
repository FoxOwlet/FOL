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
}