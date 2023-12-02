package com.foxowlet.fol.interpreter.cli;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.Interpreter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

public class InterpreterFacade extends AbstractCLI {

    public void interpret(String... filenames) {
        doInSession(session -> interpret(session, filenames));
    }

    private void interpret(Interpreter.Session session, String[] filenames) {
        for (String filename : filenames) {
            try (Reader reader = new FileReader(filename)) {
                Optional<Expression> expr = parser().parse(reader);
                if (expr.isEmpty()) {
                    System.err.println("Got syntax error, aborting further interpretation...");
                    break;
                }
                session.interpret(expr.get());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
