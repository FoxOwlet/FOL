package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Function;

public class FunctionTypeDescriptor implements TypeDescriptor {
    private final IntType idType;
    private final InterpretationContext context;
    private final TypeDescriptor argumentType;
    private final TypeDescriptor returnType;

    public FunctionTypeDescriptor(InterpretationContext context, TypeDescriptor argumentType, TypeDescriptor returnType) {
        this.context = context;
        this.argumentType = argumentType;
        this.returnType = returnType;
        this.idType = new IntType();
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return idType.size();
    }

    @Override
    public byte[] encode(Object value) {
        return idType.encode(ReflectionUtils.as(value, Function.class).id());
    }

    @Override
    public Object decode(byte[] data) {
        return context.lookupSymbol(String.valueOf(idType.decode(data)));
    }
}
