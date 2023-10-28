package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Value;

public class SymbolInterpreter implements ExpressionInterpreter<Symbol> {
    @Override
    public Value interpret(Symbol expression, InterpretationContext context) {
        return ReflectionUtils.as(context.lookupSymbol(expression.name()), Value.class);
    }
}
