package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.*;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.type.FunctionTypeDescriptor;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public final class TestUtils {
    private TestUtils() {}

    public static Type type(String name) {
        return new ScalarType(new Symbol(name));
    }

    public static Type ftype(String... names) {
        int lastIndex = names.length - 1;
        Type type = type(names[lastIndex]);
        for (int i = lastIndex; i >= 0; --i) {
            type = new FunctionType(type(names[i]), type);
        }
        return type;
    }

    public static Type ftype(Type... types) {
        int lastIndex = types.length - 1;
        Type type = types[lastIndex];
        for (int i = lastIndex; i >= 0; --i) {
            type = new FunctionType(types[i], type);
        }
        return type;
    }

    public static VarDecl var(String name, String type) {
        return new VarDecl(new Symbol(name), type(type));
    }

    public static VarDecl var(String name, Type type) {
        return new VarDecl(new Symbol(name), type);
    }

    public static Block block(Expression... exprs) {
        return new Block(Arrays.asList(exprs));
    }

    public static FunctionCall call(Expression target, Expression... params) {
        return new FunctionCall(target, Arrays.asList(params));
    }

    public static <T> void assertValue(T expected, Object value) {
        assertInstanceOf(Value.class, value);
        assertEquals(expected, ((Value) value).value());
    }

    public static void assertFunctionType(Variable function, TypeDescriptor... parts) {
        TypeDescriptor type = function.type();
        int lastIndex = parts.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            FunctionTypeDescriptor ftype = assertInstanceOf(FunctionTypeDescriptor.class, type);
            TypeDescriptor part = parts[i];
            assertEquals(part, ftype.argumentType());
            type = ftype.returnType();
        }
        assertEquals(parts[lastIndex], type);
    }
}
