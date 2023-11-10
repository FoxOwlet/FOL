package com.foxowlet.fol.ast;

public record Multiplication(Expression left, Expression right) implements ArithmeticExpression {
}
