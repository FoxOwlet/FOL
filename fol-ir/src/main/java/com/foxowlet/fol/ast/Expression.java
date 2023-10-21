package com.foxowlet.fol.ast;

public sealed interface Expression extends Node
        permits ArithmeticExpression, Assignment, Block, BooleanExpression, FieldAccess, FunctionCall, FunctionDecl, If, Lambda, Literal, StructDecl, Symbol, VarDecl {
}
