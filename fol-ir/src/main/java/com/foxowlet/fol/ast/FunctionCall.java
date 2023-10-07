package com.foxowlet.fol.ast;

public record FunctionCall(Expression target, NodeSeq<Expression> arguments) implements Expression {
}
