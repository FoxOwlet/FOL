package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.InterpreterException;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssignmentTest extends AbstractInterpreterTest {

    @Test
    void shouldReturnAssignedValue() {
        Expression assignment = new Assignment(
                var("foo", "Int"),
                new IntLiteral(42));

        Object actual = interpret(assignment);

        assertValue(42, actual);
    }

    @Test
    void shouldThrowException_whenAssignmentTargetIsInvalid() {
        Assignment assignment = new Assignment(new IntLiteral(10), new IntLiteral(20));

        assertThrows(InterpreterException.class, () -> interpret(assignment));
    }
}
