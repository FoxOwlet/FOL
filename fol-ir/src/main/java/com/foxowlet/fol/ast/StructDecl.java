package com.foxowlet.fol.ast;

public record StructDecl(Symbol name, Block body) implements Expression {
}
