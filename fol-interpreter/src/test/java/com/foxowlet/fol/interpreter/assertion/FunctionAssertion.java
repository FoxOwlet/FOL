package com.foxowlet.fol.interpreter.assertion;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.interpreter.model.Function;
import com.foxowlet.fol.interpreter.model.FunctionParameter;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class FunctionAssertion {
    private final Function function;

    FunctionAssertion(Function function) {
        this.function = function;
    }

    public FunctionAssertion hasBody(Block body) {
        assertEquals(body, function.body());
        return this;
    }

    public FunctionAssertion hasParams(FunctionParameter... params) {
        assertEquals(Arrays.asList(params), function.params());
        return this;
    }

    public FunctionAssertion hasNoParams() {
        return hasParams();
    }

    public FunctionAssertion hasReturnType(TypeDescriptor type) {
        assertEquals(type, function.returnType());
        return this;
    }

    public FunctionTypeAssertion type() {
        return new FunctionTypeAssertion(this, function.type());
    }
}
