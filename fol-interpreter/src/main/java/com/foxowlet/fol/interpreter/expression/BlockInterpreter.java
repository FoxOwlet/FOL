package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Value;

public class BlockInterpreter implements ExpressionInterpreter<Block> {
    @Override
    public Value interpret(Block expression, InterpretationContext context) {
        Value result = null;
        context.enterScope();
        for (Expression expr : expression.exprs()) {
            result = context.interpret(expr);
        }
        context.exitScope();
        return result;
    }
}
