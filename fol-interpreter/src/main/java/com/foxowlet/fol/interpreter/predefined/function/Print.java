package com.foxowlet.fol.interpreter.predefined.function;

import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.InvalidFunctionCall;
import com.foxowlet.fol.interpreter.model.FunctionParameter;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.memory.DummyMemoryLocation;
import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.type.FunctionTypeDescriptor;
import com.foxowlet.fol.interpreter.model.type.IntType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.List;

public final class Print implements PredefinedFunction {
    private final InterpretationContext context;

    public Print(InterpretationContext context) {
        this.context = context;
    }

    @Override
    public Value call(List<Value> actuals, InterpretationContext context) {
        if (actuals.size() != 1) {
            throw new InvalidFunctionCall("Wrong arguments size, expected 1, got " + actuals.size());
        }
        Value arg = actuals.get(0);
        System.out.println(arg.value());
        return arg;
    }

    @Override
    public List<FunctionParameter> params() {
        return List.of(new FunctionParameter("x", new IntType()));
    }

    @Override
    public Object value() {
        return this;
    }

    @Override
    public TypeDescriptor type() {
        return new FunctionTypeDescriptor(context, new IntType(), new IntType());
    }

    @Override
    public MemoryLocation memory() {
        return new DummyMemoryLocation();
    }

    @Override
    public String name() {
        return "print";
    }
}
