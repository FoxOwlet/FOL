package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.ByteLiteral;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.ByteType;

public class ByteLiteralInterpreter implements ExpressionInterpreter<ByteLiteral> {
    @Override
    public Value interpret(ByteLiteral expression, InterpretationContext context) {
        return new Container(expression.value(), new ByteType());
    }
}
