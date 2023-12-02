package com.foxowlet.fol.interpreter.cli;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.FunctionCall;
import com.foxowlet.fol.ast.NodeSeq;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.Interpreter;
import com.foxowlet.fol.interpreter.exception.InterpreterException;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class REPL extends AbstractCLI {
    private final Scanner scanner;

    public REPL() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        doInSession(this::run);
    }

    private void run(Interpreter.Session session) {
        while (true) {
            System.out.print("fol> ");
            String line = scanner.nextLine();
            if (line.trim().equals("exit")) {
                break;
            }
            try {
                Optional<Expression> expr = parser().parse(line);
                if (expr.isEmpty()) {
                    continue;
                }
                session.interpret(new FunctionCall(new Symbol("print"), NodeSeq.of(expr.get())));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            } catch (InterpreterException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
