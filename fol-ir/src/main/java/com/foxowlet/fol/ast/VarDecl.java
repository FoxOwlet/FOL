package com.foxowlet.fol.ast;

public record VarDecl(Symbol variable, Type type) implements Expression {
}
