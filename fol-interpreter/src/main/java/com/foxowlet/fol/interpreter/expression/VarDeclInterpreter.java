package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.VarDecl;
import com.foxowlet.fol.interpreter.Interpreter;
import com.foxowlet.fol.interpreter.model.IntType;

public class VarDeclInterpreter implements ExpressionInterpreter<VarDecl> {
    @Override
    public Object interpret(VarDecl varDecl, Interpreter.Context context) {
        if (!varDecl.type().equals("Int")) {
            throw new IllegalStateException("Unsupported type " + varDecl.type());
        }
        return context.makeVariable(varDecl.name().name(), new IntType());
    }
}
