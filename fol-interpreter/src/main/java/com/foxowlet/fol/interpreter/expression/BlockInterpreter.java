package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.InterpretationContext;

public class BlockInterpreter implements ExpressionInterpreter<Block> {
    @Override
    public Object interpret(Block expression, InterpretationContext context) {
        Object result = null;
        for (Expression expr : expression.exprs()) {
            result = context.interpret(expr);
        }
        return result;
    }
}
