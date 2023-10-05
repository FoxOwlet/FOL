package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.VarDecl;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.expression.context.ExpressionContext;
import com.foxowlet.fol.interpreter.expression.context.StructDeclContext;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;
import com.foxowlet.fol.interpreter.type.TypeInterpreter;

import java.util.List;

public class FieldDeclInterpreter implements ExpressionInterpreter<VarDecl> {
    private final TypeInterpreter typeInterpreter;

    public FieldDeclInterpreter() {
        typeInterpreter = new TypeInterpreter();
    }

    @Override
    public Object interpret(VarDecl expression, InterpretationContext context) {
        TypeDescriptor type = typeInterpreter.interpret(expression.type(), context);
        String name = expression.variable().name();
        return context.expressionContext(StructDeclContext.class).addField(name, type);
    }

    @Override
    public List<ExpressionContext> supportedContexts() {
        return List.of(new StructDeclContext());
    }
}
