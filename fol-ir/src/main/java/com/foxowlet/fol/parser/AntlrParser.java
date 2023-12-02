package com.foxowlet.fol.parser;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.parser.antlr.folLexer;
import com.foxowlet.fol.parser.antlr.folParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

public class AntlrParser implements FolParser {
    @Override
    public Optional<Expression> parse(Reader reader) throws IOException {
        CodePointCharStream stream = CharStreams.fromReader(reader);
        folLexer folLexer = new folLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(folLexer);
        folParser parser = new folParser(tokenStream);
        Expression expr = parser.file().expr;
        if (parser.getNumberOfSyntaxErrors() > 0) {
            return Optional.empty();
        }
        return Optional.of(expr);
    }

}
