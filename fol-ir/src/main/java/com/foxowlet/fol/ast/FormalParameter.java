package com.foxowlet.fol.ast;

public record FormalParameter(Symbol name, Type type) implements Node {
}
