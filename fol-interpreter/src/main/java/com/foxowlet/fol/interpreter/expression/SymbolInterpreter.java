package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.InterpretationContext;

public class SymbolInterpreter implements ExpressionInterpreter<Symbol> {
    @Override
    public Object interpret(Symbol expression, InterpretationContext context) {
        return context.lookupSymbol(expression.name());
    }
}
