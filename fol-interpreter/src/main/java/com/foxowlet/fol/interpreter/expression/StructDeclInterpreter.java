package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.FieldDecl;
import com.foxowlet.fol.ast.StructDecl;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.ast.Type;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.DuplicateFieldException;
import com.foxowlet.fol.interpreter.model.Field;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.StructType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;
import com.foxowlet.fol.interpreter.type.TypeInterpreter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StructDeclInterpreter implements ExpressionInterpreter<StructDecl> {
    private final TypeInterpreter typeInterpreter;

    public StructDeclInterpreter() {
        typeInterpreter = new TypeInterpreter();
    }

    @Override
    public Value interpret(StructDecl expression, InterpretationContext context) {
        int offset = 0;
        Set<String> fieldNames = new HashSet<>();
        List<Field> fields = new ArrayList<>();
        for (FieldDecl(Symbol(String name), Type rawType) : expression.fields()) {
            if (fieldNames.contains(name)) {
                throw new DuplicateFieldException(name, expression.name().name());
            }
            fieldNames.add(name);
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
