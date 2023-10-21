package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.TypeUtils;
import com.foxowlet.fol.interpreter.model.Value;

public class FunctionDeclInterpreter implements ExpressionInterpreter<FunctionDecl> {
    @Override
    public Value interpret(FunctionDecl expression, InterpretationContext context) {
        Lambda lambda = new Lambda(expression.params(), expression.returnType(), expression.body());
        Type type = createType(expression);
        VarDecl functionSymbol = new VarDecl(expression.name(), type);
        Assignment binding = new Assignment(functionSymbol, lambda);
        return context.interpret(binding);
    }

    private Type createType(FunctionDecl expression) {
        return TypeUtils.makeFunctionType(expression.params().subnodes(), expression.returnType());
    }
}
