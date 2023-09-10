package com.foxowlet.fol.ast;

public record FunctionType(Type argumentType, Type returnType) implements Type {
}
