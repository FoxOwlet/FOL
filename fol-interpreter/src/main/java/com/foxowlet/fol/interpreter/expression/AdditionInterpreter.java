package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Addition;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Value;

public class AdditionInterpreter extends BiOperationInterpreter<Addition, Double>
        implements ExpressionInterpreter<Addition> {
    @Override
    protected Double value(Value value) {
        return ReflectionUtils.as(value.value(), Number.class).doubleValue();
    }

    @Override
    protected Double op(Double left, Double right) {
        return left + right;
    }
}
