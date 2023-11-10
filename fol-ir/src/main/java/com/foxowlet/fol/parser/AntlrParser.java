package com.foxowlet.fol.parser;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.parser.antlr.folLexer;
import com.foxowlet.fol.parser.antlr.folParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class AntlrParser {
    public Expression parse(String input) {
        CodePointCharStream stream = CharStreams.fromString(input);
        folLexer folLexer = new folLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(folLexer);
        folParser parser = new folParser(tokenStream);
        return parser.file().expr;
    }
}
