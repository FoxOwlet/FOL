package com.foxowlet.fol.interpreter.assertion;

import com.foxowlet.fol.interpreter.model.type.FunctionTypeDescriptor;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public final class FunctionTypeAssertion {
    private final FunctionAssertion parent;
    private TypeDescriptor type;

    FunctionTypeAssertion(FunctionAssertion parent, TypeDescriptor type) {
        this.parent = parent;
        this.type = type;
    }

    public FunctionTypeAssertion withParam(TypeDescriptor paramType) {
        FunctionTypeDescriptor ftype = assertInstanceOf(FunctionTypeDescriptor.class, type);
        assertEquals(paramType, ftype.argumentType());
        type = ftype.returnType();
        return this;
    }

    public FunctionAssertion returns(TypeDescriptor returnType) {
        assertEquals(returnType, type);
        return parent;
    }
}
