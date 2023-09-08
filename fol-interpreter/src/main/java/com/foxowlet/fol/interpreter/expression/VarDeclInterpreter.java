package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.VarDecl;
import com.foxowlet.fol.interpreter.Interpreter;
import com.foxowlet.fol.interpreter.model.type.Type;

public class VarDeclInterpreter implements ExpressionInterpreter<VarDecl> {
    @Override
    public Object interpret(VarDecl varDecl, Interpreter.Context context) {
        Type type = context.lookup(varDecl.type(), Type.class);
        return context.makeVariable(varDecl.name().name(), type);
    }
}
