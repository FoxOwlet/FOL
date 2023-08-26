package com.foxowlet.fol.ast;

import java.util.List;

public record Block(List<Expression> exprs) implements Expression {
}
