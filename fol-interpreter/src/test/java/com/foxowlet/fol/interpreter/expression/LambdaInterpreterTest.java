package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.Function;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class LambdaInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldInterpretAsFunction() {
        Block body = block(literal(42));
        Lambda lambda = lambda(body);

        Object actual = interpret(lambda);

        assertEquals(new Function(1, List.of(), body), actual);
    }

    @Test
    void shouldAssignFunctionToVariable() {
        Block body = block(literal(42));
        Assignment assignment = new Assignment(
                var("foo", ftype("Unit", "Int")),
                lambda(body));

        Object actual = interpret(assignment);

        assertValue(new Function(1, List.of(), body), actual);
    }

}