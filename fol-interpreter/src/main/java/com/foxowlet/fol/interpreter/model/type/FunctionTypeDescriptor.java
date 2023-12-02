package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Function;

import java.util.Objects;

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

    public TypeDescriptor argumentType() {
        return argumentType;
    }

    public TypeDescriptor returnType() {
        return returnType;
    }

    @Override
    public String name() {
        String argType = argumentType.name();
        if (argumentType instanceof FunctionTypeDescriptor) {
            argType = String.format("(%s)", argType);
        }
        return String.format("%s->%s", argType, returnType.name());
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FunctionTypeDescriptor that = (FunctionTypeDescriptor) object;
        return Objects.equals(argumentType, that.argumentType) && Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(argumentType, returnType);
    }
}
