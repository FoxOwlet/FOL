package com.foxowlet.fol.ast;

public record Lambda(NodeSeq<FormalParameter> params, Type returnType, Block body) implements Expression {
}
