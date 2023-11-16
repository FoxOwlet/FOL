package com.foxowlet.fol.ast;

public sealed interface ArithmeticExpression extends Expression
        permits Addition, Division, Multiplication, Subtraction {
}
