package com.foxowlet.fol.ast;

public record Lambda(NodeSeq<FormalParameter> params, Block body) implements Expression {
}
