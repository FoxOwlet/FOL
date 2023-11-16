package com.foxowlet.fol.ast;

public record Equals(Expression left, Expression right) implements BooleanExpression, BiOperation {
}
