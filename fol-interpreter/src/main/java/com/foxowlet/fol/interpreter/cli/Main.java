package com.foxowlet.fol.interpreter.cli;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            new REPL().run();
        } else {
            new InterpreterFacade().interpret(args);
        }
    }
}
