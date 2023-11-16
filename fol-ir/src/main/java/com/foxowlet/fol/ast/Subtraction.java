package com.foxowlet.fol.ast;

public record Subtraction(Expression left, Expression right) implements ArithmeticExpression {
}
