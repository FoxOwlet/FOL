package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.emulator.Memory;
import com.foxowlet.fol.emulator.PhysicalMemory;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.Variable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {
    private Interpreter interpreter = new Interpreter();
    private Memory memory = new PhysicalMemory(100);

    @Test
    void intLiteral_shouldBeConvertedIntoValue() {
        Expression literal = new IntLiteral(42);

        Object actual = interpreter.interpret(memory, literal);

        assertValue(42, actual);
    }

    @Test
    void varDecl_shouldAllocateVariable() {
        Expression varDecl = new VarDecl(new Symbol("foo"), "Int");

        Object actual = interpreter.interpret(memory, varDecl);

        Variable variable = assertInstanceOf(Variable.class, actual);
        assertEquals("foo", variable.name());
    }

    @Test
    void assignment_shouldReturnAssignedValue() {
        Expression assignment = new Assignment(
                new VarDecl(new Symbol("foo"), "Int"),
                new IntLiteral(42));

        Object actual = interpreter.interpret(memory, assignment);

        assertValue(42, actual);
    }

    @Test
    void block_shouldReturnLastExpressionValue() {
        // var i: Int = 40 + 42
        Expression expr1 = new Assignment(
                new VarDecl(new Symbol("i"), "Int"),
                new Addition(new IntLiteral(40), new IntLiteral(42)));
        // var j: Int = i + 10;
        Expression expr2 = new Assignment(
                new VarDecl(new Symbol("j"), "Int"),
                new Addition(new Symbol("i"), new IntLiteral(10)));
        // { ... }
        Expression block = new Block(List.of(expr1, expr2));

        Object actual = interpreter.interpret(memory, block);

        assertValue(92, actual);
    }

    @Nested
    class ExceptionTest {
        @Test
        void shouldThrowException_whenVariableIsUndefined() {
            Symbol variable = new Symbol("foo");

            assertThrows(Exception.class, () -> interpreter.interpret(memory, variable));
        }

        @Test
        void shouldThrowException_whenTypeIsUnresolved() {
            VarDecl varDecl = new VarDecl(new Symbol("foo"), "bar");

            assertThrows(Exception.class, () -> interpreter.interpret(memory, varDecl));
        }

        @Test
        void shouldThrowException_whenAssignmentTargetIsInvalid() {
            Assignment assignment = new Assignment(new IntLiteral(10), new IntLiteral(20));

            assertThrows(Exception.class, () -> interpreter.interpret(memory, assignment));
        }
    }

    private static <T> void assertValue(T expected, Object value) {
        assertInstanceOf(Value.class, value);
        assertEquals(expected, ((Value) value).value());
    }
}