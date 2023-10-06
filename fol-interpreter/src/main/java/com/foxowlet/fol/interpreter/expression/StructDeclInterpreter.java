package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.StructDecl;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.expression.context.StructDeclContext;
import com.foxowlet.fol.interpreter.model.type.StructType;

public class StructDeclInterpreter implements ExpressionInterpreter<StructDecl> {
    @Override
    public Object interpret(StructDecl expression, InterpretationContext context) {
        StructDeclContext ctx = context.withExpressionContext(new StructDeclContext(), () -> {
            context.interpret(expression.body());
            return context.expressionContext(StructDeclContext.class);
        });
        StructType type = new StructType(expression.name().name(), ctx.totalSize(), ctx.fields());
        context.registerSymbol(type.name(), type);
        return type;
    }
}
