package com.foxowlet.fol.ast;

public record Division(Expression left, Expression right) implements ArithmeticExpression, BiOperation {
}
