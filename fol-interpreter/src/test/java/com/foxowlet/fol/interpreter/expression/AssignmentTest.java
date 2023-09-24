package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Assignment;
import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.literal;
import static com.foxowlet.fol.interpreter.AstUtils.var;

class AssignmentTest extends AbstractInterpreterTest {

    @Test
    void shouldReturnAssignedValue() {
        Expression assignment = new Assignment(var("foo", "Int"), literal(42));

        Object actual = interpret(assignment);

        assertValue(42, actual);
    }

    @Test
    void shouldThrowException_whenAssignmentTargetIsInvalid() {
        Assignment assignment = new Assignment(literal(10), literal(20));

        assertError(assignment);
    }
}
