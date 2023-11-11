package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.parser.AntlrParser;

public class Main {
    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter(new Emulator());
        AntlrParser parser = new AntlrParser();
        String input = """
                struct Foo(i: Int, j: Int)
                var foo: Foo = Foo(10, 30)
                var i: Int = foo.i + foo.j
                i + 2
                """;
        System.out.println(interpreter.interpret(parser.parse(input)).value());
    }
}
