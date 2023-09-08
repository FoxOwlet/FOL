package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.Interpreter;
import com.foxowlet.fol.interpreter.model.Variable;

public class SymbolInterpreter implements ExpressionInterpreter<Symbol> {
    @Override
    public Object interpret(Symbol expression, Interpreter.Context context) {
        return context.lookup(expression.name(), Variable.class);
    }
}
