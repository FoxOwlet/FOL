package com.foxowlet.fol.interpreter.type;

import com.foxowlet.fol.ast.FunctionType;
import com.foxowlet.fol.ast.ScalarType;
import com.foxowlet.fol.ast.Type;
import com.foxowlet.fol.interpreter.model.type.FunctionTypeDescriptor;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;
import com.foxowlet.fol.interpreter.InterpretationContext;

public class TypeInterpreter {
    public TypeDescriptor interpret(Type type, InterpretationContext context) {
        return switch (type) {
            case ScalarType scalar ->
                    context.interpret(scalar.symbol(), TypeDescriptor.class);
            case FunctionType(Type argumentType, Type returnType) ->
                new FunctionTypeDescriptor(context, interpret(argumentType, context), interpret(returnType, context));
        };
    }
}
