package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.IntLiteral;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.type.IntType;

public class IntLiteralInterpreter implements ExpressionInterpreter<IntLiteral> {
    @Override
    public Object interpret(IntLiteral expression, InterpretationContext context) {
        return new Container(expression.value(), new IntType());
    }
}
