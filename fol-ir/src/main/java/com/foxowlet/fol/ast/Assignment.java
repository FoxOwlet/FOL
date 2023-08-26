package com.foxowlet.fol.ast;

public record Assignment(Expression lhs, Expression rhs) implements Expression {
}
