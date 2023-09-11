package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.foxowlet.fol.interpreter.TestUtils.*;


class FunctionCallInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldCallFunctionFromVariable() {
        // val foo: Unit->Int
        VarDecl var = var("foo", ftype("Unit", "Int"));
        Block body = block(new IntLiteral(42));
        // #(){ 42 }
        Lambda lambda = new Lambda(List.of(), body);
        // val foo: Unit->Int = #(){ 42 }
        Assignment assignment = new Assignment(var, lambda);
        // foo()
        FunctionCall functionCall = call(new Symbol("foo"));
        // { ... }
        Block block = block(assignment, functionCall);

        Object actual = interpret(block);

        assertValue(42, actual);
    }


    @Test
    void shouldCallFunction_whenCalledInplace() {
        Block body = block(new IntLiteral(42));
        Lambda lambda = new Lambda(List.of(), body);
        // #(){ 42 }()
        FunctionCall functionCall = call(lambda);

        Object actual = interpret(functionCall);

        assertValue(42, actual);
    }
}