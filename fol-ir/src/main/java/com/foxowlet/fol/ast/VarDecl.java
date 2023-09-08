package com.foxowlet.fol.ast;

public record VarDecl(Symbol name, Symbol type) implements Expression {
}
