package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Subtraction;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Value;

public class SubtractionInterpreter extends BiOperationInterpreter<Subtraction, Double>
        implements ExpressionInterpreter<Subtraction> {
    @Override
    protected Double value(Value value) {
        return ReflectionUtils.as(value.value(), Number.class).doubleValue();
    }

    @Override
    protected Double op(Double left, Double right) {
        return left - right;
    }
}
