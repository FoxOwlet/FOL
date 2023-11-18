package com.foxowlet.fol.ast;

public sealed interface Expression extends Node
        permits ArithmeticExpression, Assignment, Block, FieldAccess, File,
        FunctionCall, FunctionDecl, Lambda, Literal, StructDecl, Symbol, VarDecl,
        If, BooleanExpression {
}
