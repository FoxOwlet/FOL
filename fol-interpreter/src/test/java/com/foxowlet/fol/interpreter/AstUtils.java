package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.*;

import java.util.Arrays;
import java.util.List;

public final class AstUtils {
    private AstUtils() {}

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

    public static Assignment var(String name, String type, Expression value) {
        return new Assignment(var(name, type), value);
    }

    public static Assignment var(String name, Type type, Expression value) {
        return new Assignment(var(name, type), value);
    }

    public static Block block(Expression... exprs) {
        return new Block(Arrays.asList(exprs));
    }

    public static FunctionCall call(Expression target, Expression... params) {
        return new FunctionCall(target, Arrays.asList(params));
    }

    public static Lambda lambda(List<FormalParameter> params, Block body) {
        return new Lambda(params, body);
    }

    public static Lambda lambda(Block body, FormalParameter... params) {
        return lambda(Arrays.asList(params), body);
    }

    public static FormalParameter formal(String name, Type type) {
        return new FormalParameter(new Symbol(name), type);
    }

    public static FormalParameter formal(String name, String type) {
        return formal(name, type(type));
    }

    public static FunctionDecl fdecl(String name, List<FormalParameter> params, Type type, Block body) {
        return new FunctionDecl(new Symbol(name), params, type, body);
    }

    public static FunctionDecl fdecl(String name, List<FormalParameter> params, String type, Block body) {
        return fdecl(name, params, type(type), body);
    }

    public static FunctionDecl fdecl(String name, Type type, Block body, FormalParameter... params) {
        return fdecl(name, List.of(params), type, body);
    }

    public static FunctionDecl fdecl(String name, String type, Block body, FormalParameter... params) {
        return fdecl(name, List.of(params), type(type), body);
    }

    public static FunctionDecl fdecl(String name) {
        return fdecl(name, List.of(), type("Unit"), block());
    }

    public static Expression literal(Object value) {
        return switch (value) {
            case Integer i -> new IntLiteral(i);
            case Long l -> new LongLiteral(l);
            case Byte b -> new ByteLiteral(b);
            default -> throw new IllegalArgumentException("Can't create literal for " + value);
        };
    }
}
