package com.foxowlet.fol.ast;

public record VarDecl(Symbol name, String type) implements Expression {
}
