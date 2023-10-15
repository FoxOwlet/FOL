package com.foxowlet.fol.ast;

public record FieldAccess(Expression target, Symbol field) implements Expression {
}
