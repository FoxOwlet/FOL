package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Assignment;
import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.Lambda;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.type.IntType;
import com.foxowlet.fol.interpreter.model.type.UnitType;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertFunction;
import static com.foxowlet.fol.interpreter.AstUtils.*;


class LambdaInterpreterTest extends AbstractInterpreterTest {
    @Test
    void shouldInterpretAsFunction() {
        // { 42 }
        Block body = block(literal(42));
        // #(): Int { 42 }
        Lambda lambda = lambda(body);

        Object actual = interpret(lambda);

        assertFunction(actual)
                .hasNoParams()
                .hasBody(body)
                .hasReturnType(new IntType());
    }

    @Test
    void shouldAssignFunctionToVariable() {
        // { 42 }
        Block body = block(literal(42));
        // var foo: Unit->Int = #(): Int { 42 }
        Assignment assignment = new Assignment(
                var("foo", ftype("Unit", "Int")),
                lambda(body));

        Object actual = interpret(assignment);

        assertFunction(actual)
                .hasBody(body)
                .hasNoParams()
                .type()
                .withParam(new UnitType())
                .returns(new IntType());
    }

}