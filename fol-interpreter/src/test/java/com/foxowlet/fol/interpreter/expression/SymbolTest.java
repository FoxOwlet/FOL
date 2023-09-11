package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SymbolTest extends AbstractInterpreterTest {
    @Test
    void shouldReturnPredefinedSymbol() {
        Symbol type = new Symbol("Int");

        Object actual = interpret(type);

        assertEquals(new IntType(), actual);
    }

    @Test
    void shouldThrowException_whenSymbolIsUndefined() {
        Symbol variable = new Symbol("foo");

        assertError(variable);
    }

}
