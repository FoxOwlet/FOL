package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.emulator.Emulator;

public class AbstractInterpreterTest {
    private final Interpreter interpreter;

    public AbstractInterpreterTest() {
        this.interpreter = new Interpreter(new Emulator(), getInterpreterConfiguration());
    }

    protected InterpreterConfiguration getInterpreterConfiguration() {
        return new InterpreterConfiguration();
    }

    protected final Object interpret(Expression expression) {
        return interpreter.interpret(expression);
    }
}
