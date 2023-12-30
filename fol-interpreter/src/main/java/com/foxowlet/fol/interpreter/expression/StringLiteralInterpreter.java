package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.StringLiteral;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.StringType;

public class StringLiteralInterpreter implements ExpressionInterpreter<StringLiteral> {
    @Override
    public Value interpret(StringLiteral expression, InterpretationContext context) {
        String value = expression.value();
        StringType type = context.makeString();
        return new Container(type.encode(context, value), type);
    }
}
