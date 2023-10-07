package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.InterpretationContext;

public class BlockInterpreter implements ExpressionInterpreter<Block> {
    @Override
    public Object interpret(Block expression, InterpretationContext context) {
        Object result = null;
        context.enterScope();
        for (Expression expr : expression.exprs().subnodes()) {
            result = context.interpret(expr);
        }
        context.exitScope();
        return result;
    }
}
