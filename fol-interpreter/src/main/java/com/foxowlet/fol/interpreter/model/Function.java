package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.ast.Block;

import java.util.List;

public record Function(int id, List<FunctionParameter> params, Block body) implements Value {
    @Override
    public Object value() {
        return this;
    }
}
