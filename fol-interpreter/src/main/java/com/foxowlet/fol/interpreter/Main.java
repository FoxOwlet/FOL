package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.emulator.Emulator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InterpreterConfiguration config = new InterpreterConfiguration()
                .withMemoryLimit(100_000);
        Interpreter interpreter = new Interpreter(new Emulator(), config);

        /*
        def fib(i: Int): Int {
          if (i == 0)      0
          else if (i == 1) 1
          else             fib(i + -1) + fib(i + -2)
        }
         */
        FunctionDecl fib = new FunctionDecl(new Symbol("fib"), NodeSeq.of(
                new FormalParameter(new Symbol("i"), new ScalarType(new Symbol("Int")))),
                new ScalarType(new Symbol("Int")),
                new Block(NodeSeq.of(
                        new If(
                                new Equals(new Symbol("i"), new IntLiteral(0)),
                                new IntLiteral(0),
                                new If(
                                        new Equals(new Symbol("i"), new IntLiteral(1)),
                                        new IntLiteral(1),
                                        new Addition(
                                                new FunctionCall(new Symbol("fib"),
                                                        NodeSeq.of(new Addition(
                                                                new Symbol("i"),
                                                                new IntLiteral(-1)))),
                                                new FunctionCall(new Symbol("fib"),
                                                        NodeSeq.of(new Addition(
                                                                new Symbol("i"),
                                                                new IntLiteral(-2))))))))));
        interpreter.interpret(fib);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine();
            if (line.isBlank()) {
                break;
            }
            // fib(<arg>)
            int arg = Integer.parseInt(line);
            FunctionCall call = new FunctionCall(new Symbol("fib"), NodeSeq.of(new IntLiteral(arg)));

            System.out.println(interpreter.interpret(call));
        }
    }
}
