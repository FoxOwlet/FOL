package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.LongLiteral;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.LongType;

public class LongLiteralInterpreter implements ExpressionInterpreter<LongLiteral> {
    @Override
    public Value interpret(LongLiteral expression, InterpretationContext context) {
        return new Container(expression.value(), new LongType());
    }
}
