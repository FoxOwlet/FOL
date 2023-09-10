package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.Function;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LambdaInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldInterpretAsFunction() {
        Block body = new Block(List.of(new IntLiteral(42)));
        Lambda lambda = new Lambda(List.of(), body);

        Object actual = interpret(lambda);

        assertEquals(new Function(1, List.of(), body), actual);
    }

    @Test
    void shouldAssignFunctionToVariable() {
        ScalarType argumentType = new ScalarType(new Symbol("Unit"));
        ScalarType returnType = new ScalarType(new Symbol("Int"));
        VarDecl var = new VarDecl(new Symbol("foo"), new FunctionType(argumentType, returnType));
        Block body = new Block(List.of(new IntLiteral(42)));
        Lambda lambda = new Lambda(List.of(), body);
        Assignment assignment = new Assignment(var, lambda);

        Object actual = interpret(assignment);

        assertValue(new Function(1, List.of(), body), actual);
    }

}