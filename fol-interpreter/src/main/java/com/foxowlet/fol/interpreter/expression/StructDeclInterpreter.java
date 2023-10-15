package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.FieldDecl;
import com.foxowlet.fol.ast.StructDecl;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.ast.Type;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.Field;
import com.foxowlet.fol.interpreter.model.type.StructType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;
import com.foxowlet.fol.interpreter.type.TypeInterpreter;

import java.util.ArrayList;
import java.util.List;

public class StructDeclInterpreter implements ExpressionInterpreter<StructDecl> {
    private final TypeInterpreter typeInterpreter;

    public StructDeclInterpreter() {
        typeInterpreter = new TypeInterpreter();
    }

    @Override
    public Object interpret(StructDecl expression, InterpretationContext context) {
        int offset = 0;
        List<Field> fields = new ArrayList<>();
        for (FieldDecl(Symbol(String name), Type rawType) : expression.fields().subnodes()) {
            TypeDescriptor type = typeInterpreter.interpret(rawType, context);
            Field field = new Field(offset, name, type);
            offset += type.size();
            fields.add(field);
        }
        StructType type = new StructType(expression.name().name(), offset, fields);
        context.registerSymbol(type.name(), type);
        return type;
    }
}
