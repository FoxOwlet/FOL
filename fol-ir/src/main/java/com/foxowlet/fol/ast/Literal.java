package com.foxowlet.fol.ast;

public sealed interface Literal extends Expression
        permits ByteLiteral, IntLiteral, LongLiteral {
}
