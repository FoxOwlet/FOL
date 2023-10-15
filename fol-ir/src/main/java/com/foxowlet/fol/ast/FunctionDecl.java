package com.foxowlet.fol.ast;

public record FunctionDecl(Symbol name, NodeSeq<FormalParameter> params, Type returnType, Block body)
        implements Expression {
}
