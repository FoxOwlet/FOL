package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.interpreter.InterpretationContext;

import java.util.List;

public interface Callable {
    Value call(List<Value> actuals, InterpretationContext context);

    List<FunctionParameter> params();
}
