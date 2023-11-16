package com.foxowlet.fol.ast;

public record If(Expression condition, Expression thenBranch, Expression elseBranch) implements Expression {
}
