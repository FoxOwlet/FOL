package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.VarDecl;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.MemoryLocation;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.type.Type;

public class VarDeclInterpreter implements ExpressionInterpreter<VarDecl> {
    @Override
    public Object interpret(VarDecl varDecl, InterpretationContext context) {
        Type type = context.interpret(varDecl.type(), Type.class);
        MemoryLocation memory = context.allocateMemory(type.size());
        String varName = varDecl.name().name();
        Variable variable = new Variable(memory, varName, type);
        context.registerSymbol(varName, variable);
        return variable;
    }
}
