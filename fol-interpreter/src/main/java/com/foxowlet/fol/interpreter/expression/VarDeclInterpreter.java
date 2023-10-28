package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.VarDecl;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;
import com.foxowlet.fol.interpreter.type.TypeInterpreter;

public class VarDeclInterpreter implements ExpressionInterpreter<VarDecl> {
    private final TypeInterpreter typeInterpreter;

    public VarDeclInterpreter() {
        typeInterpreter = new TypeInterpreter();
    }

    @Override
    public Value interpret(VarDecl varDecl, InterpretationContext context) {
        TypeDescriptor type = typeInterpreter.interpret(varDecl.type(), context);
        MemoryLocation memory = context.allocateMemory(type.size());
        String varName = varDecl.variable().name();
        Variable variable = new Variable(memory, varName, type);
        context.registerSymbol(varName, variable);
        return variable;
    }
}
