package com.foxowlet.fol.parser;

import com.foxowlet.fol.ast.Expression;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;

public interface FolParser {
    Optional<Expression> parse(Reader reader) throws IOException;

    default Optional<Expression> parse(String input) throws IOException {
        return parse(new StringReader(input));
    }
}
