package com.foxowlet.fol.ast;

public record Block(NodeSeq<Expression> exprs) implements Expression {
}
