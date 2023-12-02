package com.foxowlet.fol.interpreter.cli;

import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.interpreter.Interpreter;
import com.foxowlet.fol.parser.AntlrParser;
import com.foxowlet.fol.parser.FolParser;

import java.util.function.Consumer;

public abstract class AbstractCLI {
    private final Interpreter interpreter;
    private final FolParser parser;

    protected AbstractCLI() {
        this(new Interpreter(new Emulator()), new AntlrParser());
    }

    protected AbstractCLI(Interpreter interpreter, FolParser parser) {
        this.interpreter = interpreter;
        this.parser = parser;
    }

    protected final void doInSession(Consumer<Interpreter.Session> action) {
        action.accept(interpreter.startSession());
    }

    protected final FolParser parser() {
        return parser;
    }
}
