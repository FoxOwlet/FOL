package com.foxowlet.fol.ast;

public record VarDecl(Symbol variable, Symbol type) implements Expression {
}
