package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.VarDecl;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.InterpreterException;
import com.foxowlet.fol.interpreter.model.Variable;
import org.junit.jupiter.api.Test;

import static com.foxowlet.fol.interpreter.TestUtils.var;
import static org.junit.jupiter.api.Assertions.*;

class VarDeclTest extends AbstractInterpreterTest {

    @Test
    void shouldAllocateVariable() {
        Expression varDecl = var("foo", "Long");

        Object actual = interpret(varDecl);

        Variable variable = assertInstanceOf(Variable.class, actual);
        assertEquals("foo", variable.name());
    }

    @Test
    void shouldThrowException_whenTypeIsUnresolved() {
        VarDecl varDecl = var("foo", "bar");

        assertThrows(InterpreterException.class, () -> interpret(varDecl));
    }
}
