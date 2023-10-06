package com.foxowlet.fol.interpreter.expression.context;

import com.foxowlet.fol.interpreter.model.FieldDecl;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;

public class StructDeclContext implements ExpressionContext {
    private final List<FieldDecl> fields;
    private int offset;

    public StructDeclContext() {
        fields = new ArrayList<>();
        offset = 0;
    }

    public FieldDecl addField(String name, TypeDescriptor type) {
        FieldDecl fieldDecl = new FieldDecl(offset, name, type);
        fields.add(fieldDecl);
        offset += type.size();
        return fieldDecl;
    }

    public int totalSize() {
        return offset;
    }

    public List<FieldDecl> fields() {
        return fields;
    }
}
