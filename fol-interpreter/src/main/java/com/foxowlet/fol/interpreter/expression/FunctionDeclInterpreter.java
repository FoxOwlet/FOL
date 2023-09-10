package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.InterpretationContext;

public class FunctionDeclInterpreter implements ExpressionInterpreter<FunctionDecl> {
    @Override
    public Object interpret(FunctionDecl expression, InterpretationContext context) {
        Lambda lambda = new Lambda(expression.params(), expression.body());
        Type type = createType(expression);
        VarDecl functionSymbol = new VarDecl(expression.name(), type);
        Assignment binding = new Assignment(functionSymbol, lambda);
        return context.interpret(binding);
    }

    private Type createType(FunctionDecl expression) {
        FormalParameter[] params = expression.params().toArray(new FormalParameter[0]);
        Type type = expression.returnType();
        if (params.length == 0) {
            return new FunctionType(new ScalarType(new Symbol("Unit")), type);
        }
        // left fold types to produce correct function type
        for (int i = params.length - 1; i >= 0; --i) {
            type = new FunctionType(params[i].type(), type);
        }
        return type;
    }
}
