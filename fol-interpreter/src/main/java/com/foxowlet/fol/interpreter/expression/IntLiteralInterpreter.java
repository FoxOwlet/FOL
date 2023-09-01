package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.IntLiteral;
import com.foxowlet.fol.interpreter.Interpreter;
import com.foxowlet.fol.interpreter.model.Container;

public class IntLiteralInterpreter implements ExpressionInterpreter<IntLiteral> {
    @Override
    public Object interpret(IntLiteral expression, Interpreter.Context context) {
        return new Container(expression.value());
    }
}
