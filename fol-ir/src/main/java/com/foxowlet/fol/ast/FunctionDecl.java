package com.foxowlet.fol.ast;

import java.util.List;

public record FunctionDecl(Symbol name, List<FormalParameter> params, Type returnType, Block body) implements Expression {
}
