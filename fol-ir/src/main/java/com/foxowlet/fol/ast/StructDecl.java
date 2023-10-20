package com.foxowlet.fol.ast;

public record StructDecl(Symbol name, NodeSeq<FieldDecl> fields) implements Expression {
}
