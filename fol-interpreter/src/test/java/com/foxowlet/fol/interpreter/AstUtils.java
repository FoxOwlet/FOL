package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.*;

import java.util.Arrays;

public final class AstUtils {
    private AstUtils() {}

    public static Type type(String name) {
        return new ScalarType(new Symbol(name));
    }

    public static Type ftype(String... names) {
        return ftype(Arrays.stream(names).map(AstUtils::type).toArray(Type[]::new));
    }

    public static Type ftype(Type... types) {
        int lastIndex = types.length - 1;
        Type type = types[lastIndex];
        for (int i = lastIndex - 1; i >= 0; --i) {
            type = new FunctionType(types[i], type);
        }
        return type;
    }

    public static FieldDecl field(String name, String type) {
        return new FieldDecl(new Symbol(name), type(type));
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
        return new Block(NodeSeq.of(exprs));
    }

    public static FunctionCall call(Expression target, Expression... params) {
        return new FunctionCall(target, NodeSeq.of(params));
    }

    public static Lambda lambda(NodeSeq<FormalParameter> params, Type type, Block body) {
        return new Lambda(params, type, body);
    }

    public static Lambda lambda(Block body, FormalParameter... params) {
        return lambda(NodeSeq.of(params), type("Int"), body);
    }

    public static FormalParameter formal(String name, Type type) {
        return new FormalParameter(new Symbol(name), type);
    }

    public static FormalParameter formal(String name, String type) {
        return formal(name, type(type));
    }

    public static FunctionDecl fdecl(String name, NodeSeq<FormalParameter> params, Type type, Block body) {
        return new FunctionDecl(new Symbol(name), params, type, body);
    }

    public static FunctionDecl fdecl(String name, NodeSeq<FormalParameter> params, String type, Block body) {
        return fdecl(name, params, type(type), body);
    }

    public static FunctionDecl fdecl(String name, Type type, Block body, FormalParameter... params) {
        return fdecl(name, NodeSeq.of(params), type, body);
    }

    public static FunctionDecl fdecl(String name, String type, Block body, FormalParameter... params) {
        return fdecl(name, NodeSeq.of(params), type(type), body);
    }

    public static FunctionDecl fdecl(String name) {
        return fdecl(name, NodeSeq.of(), type("Unit"), block());
    }

    public static FieldAccess fieldAccess(String target, String field) {
        return fieldAccess(new Symbol(target), field);
    }

    public static FieldAccess fieldAccess(Expression target, String field) {
        return new FieldAccess(target, new Symbol(field));
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
