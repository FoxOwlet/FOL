package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import java.util.List;


class FunctionCallInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldCallFunctionFromVariable() {
        ScalarType argumentType = new ScalarType(new Symbol("Unit"));
        ScalarType returnType = new ScalarType(new Symbol("Int"));
        // val foo: Unit->Int
        VarDecl var = new VarDecl(new Symbol("foo"), new FunctionType(argumentType, returnType));
        Block body = new Block(List.of(new IntLiteral(42)));
        // #(){ 42 }
        Lambda lambda = new Lambda(List.of(), body);
        // val foo: Unit->Int = #(){ 42 }
        Assignment assignment = new Assignment(var, lambda);
        // foo()
        FunctionCall functionCall = new FunctionCall(new Symbol("foo"), List.of());
        // { ... }
        Block block = new Block(List.of(assignment, functionCall));

        Object actual = interpret(block);

        assertValue(42, actual);
    }


    @Test
    void shouldCallFunction_whenCalledInplace() {
        Block body = new Block(List.of(new IntLiteral(42)));
        Lambda lambda = new Lambda(List.of(), body);
        // #(){ 42 }()
        FunctionCall functionCall = new FunctionCall(lambda, List.of());

        Object actual = interpret(functionCall);

        assertValue(42, actual);
    }
}