package com.foxowlet.fol.interpreter.internal;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.FunctionParameter;
import com.foxowlet.fol.interpreter.model.type.FunctionTypeDescriptor;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;
import com.foxowlet.fol.interpreter.model.type.UnitType;

import java.util.List;
import java.util.ListIterator;

public final class TypeUtils {
    private TypeUtils() {}

    public static FunctionType makeFunctionType(List<FormalParameter> params, Type returnType) {
        Type type = returnType;
        if (params.isEmpty()) {
            return new FunctionType(new ScalarType(new Symbol("Unit")), type);
        }
        ListIterator<FormalParameter> paramTypesIterator = params.listIterator(params.size());
        while (paramTypesIterator.hasPrevious()) {
            type = new FunctionType(paramTypesIterator.previous().type(), type);
        }
        return (FunctionType) type;
    }

    public static FunctionTypeDescriptor makeFunctionTypeDescriptor(List<FunctionParameter> params,
                                                                    TypeDescriptor returnType,
                                                                    InterpretationContext context) {
        TypeDescriptor type = returnType;
        if (params.isEmpty()) {
            return new FunctionTypeDescriptor(context, new UnitType(), type);
        }
        ListIterator<FunctionParameter> paramsIterator = params.listIterator(params.size());
        while (paramsIterator.hasPrevious()) {
            type = new FunctionTypeDescriptor(context, paramsIterator.previous().type(), type);
        }
        return (FunctionTypeDescriptor) type;
    }
}
