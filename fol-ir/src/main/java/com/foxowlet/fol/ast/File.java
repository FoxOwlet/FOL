package com.foxowlet.fol.ast;

public record File(NodeSeq<Expression> content) implements Expression {
}
