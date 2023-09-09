package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Variable;

public class FunctionDeclInterpreter implements ExpressionInterpreter<FunctionDecl> {
    @Override
    public Object interpret(FunctionDecl expression, InterpretationContext context) {
        Lambda lambda = new Lambda(expression.params(), expression.body());
        Type type = createType(expression);
        VarDecl functionSymbol = new VarDecl(expression.name(), type);
        Assignment binding = new Assignment(functionSymbol, lambda);
        // Technically, we don't need an exact class here
        // But perform cast to ensure that assignment returns variable
        // So we will fail fast in the implementation changes
        // Yeah, hidden coupling ¯\_(ツ)_/¯
        // But otherwise we will need to duplicate LambdaInterpreter logic here
        // TODO: try to rework this (possibly by extracting lambda interpretation logic somewhere)
        return context.interpret(binding, Variable.class);
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
