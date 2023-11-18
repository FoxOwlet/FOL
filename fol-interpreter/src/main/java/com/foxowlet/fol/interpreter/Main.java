package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.parser.AntlrParser;

public class Main {
    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter(new Emulator());
        AntlrParser parser = new AntlrParser();
        String input = """
                def factorial(n: Int): Int {
                  if (n == 0) 1 else n * factorial(n - 1)
                }
                
                def fibonacci(n: Int): Int {
                  if (n == 0)      0
                  else if (n == 1) 1
                  else             fibonacci(n - 2) + fibonacci(n - 1)
                }
                
                print(factorial(10))
                print(fibonacci(5))
                """;
        interpreter.interpret(parser.parse(input));
    }
}
