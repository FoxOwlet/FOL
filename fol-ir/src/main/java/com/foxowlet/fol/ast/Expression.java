package com.foxowlet.fol.ast;

public sealed interface Expression extends Node
        permits VarDecl, Assignment, Literal, Symbol, ArithmeticExpression, Block,
        Lambda, FunctionCall, FunctionDecl {
}
