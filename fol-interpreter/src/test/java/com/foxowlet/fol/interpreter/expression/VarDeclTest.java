package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.ast.VarDecl;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.exception.InterpreterException;
import com.foxowlet.fol.interpreter.model.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclTest extends AbstractInterpreterTest {

    @Test
    void shouldAllocateVariable() {
        Expression varDecl = new VarDecl(new Symbol("foo"), new Symbol("Long"));

        Object actual = interpret(varDecl);

        Variable variable = assertInstanceOf(Variable.class, actual);
        assertEquals("foo", variable.name());
    }

    @Test
    void shouldThrowException_whenTypeIsUnresolved() {
        VarDecl varDecl = new VarDecl(new Symbol("foo"), new Symbol("bar"));

        assertThrows(InterpreterException.class, () -> interpret(varDecl));
    }
}
