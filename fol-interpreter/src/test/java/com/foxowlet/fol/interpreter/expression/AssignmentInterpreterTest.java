package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Assignment;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.exception.TypeException;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertValue;
import static com.foxowlet.fol.interpreter.AstUtils.literal;
import static com.foxowlet.fol.interpreter.AstUtils.var;

class AssignmentInterpreterTest extends AbstractInterpreterTest {

    @Test
    void shouldReturnAssignedValue() {
        // var foo: Int = 42
        Assignment assignment = new Assignment(var("foo", "Int"), literal(42));

        Object actual = interpret(assignment);

        assertValue(actual).is(42);
    }

    @Test
    void shouldThrowException_whenAssignmentTargetIsInvalid() {
        // 10 = 20
        Assignment assignment = new Assignment(literal(10), literal(20));

        assertError(TypeException.class, assignment);
    }

    @Test
    void shouldThrowException_whenIncompatibleTypes() {
        // var foo: Int = 42L
        Assignment assignment = new Assignment(var("foo", "Int"), literal(42L));

        assertError(IncompatibleTypeException.class, assignment);
    }
}
