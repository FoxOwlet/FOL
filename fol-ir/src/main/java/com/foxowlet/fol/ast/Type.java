package com.foxowlet.fol.ast;

public sealed interface Type extends Node
        permits ScalarType, FunctionType {
}
