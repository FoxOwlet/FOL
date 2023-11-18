package com.foxowlet.fol.ast;

public record Addition(Expression left, Expression right) implements ArithmeticExpression, BiOperation {
}
