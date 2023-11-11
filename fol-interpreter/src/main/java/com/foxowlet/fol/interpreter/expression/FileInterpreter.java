package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.File;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Value;

public final class FileInterpreter implements ExpressionInterpreter<File> {
    @Override
    public Value interpret(File expression, InterpretationContext context) {
        Value result = null;
        for (Expression expr : expression.content()) {
            result = context.interpret(expr);
        }
        return result;
    }
}
