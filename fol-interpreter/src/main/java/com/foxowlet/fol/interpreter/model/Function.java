package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.internal.BiIterator;
import com.foxowlet.fol.interpreter.model.memory.DummyMemoryLocation;
import com.foxowlet.fol.interpreter.model.memory.MemoryBlock;
import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.type.FunctionTypeDescriptor;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.List;

public record Function(int id,
                       List<FunctionParameter> params,
                       TypeDescriptor type,
                       Block body)
        implements Value, Callable {

    @Override
    public Object value() {
        return this;
    }

    public TypeDescriptor returnType() {
        TypeDescriptor type = this.type;
        while (type instanceof FunctionTypeDescriptor ftype) {
            type = ftype.returnType();
        }
        return type;
    }

    @Override
    public MemoryLocation memory() {
        return new DummyMemoryLocation();
    }

    @Override
    public Value call(List<Value> actuals, InterpretationContext context) {
        bindParams(params(), actuals, context);
        Value value = context.interpret(body(), Value.class);
        TypeDescriptor returnType = returnType();
        if (!returnType.isCompatibleWith(value.type())) {
            throw new IncompatibleTypeException(value, returnType);
        }
        return value;
    }

    private void bindParams(List<FunctionParameter> params,
                            List<Value> arguments,
                            InterpretationContext context) {
        new BiIterator<>(params, arguments)
                .forEachRemaining((param, arg) -> bind(makeParameter(param, context), arg));
    }

    private Variable makeParameter(FunctionParameter formal, InterpretationContext context) {
        MemoryBlock memoryBlock = context.allocateMemory(formal.type().size());
        Variable variable = new Variable(memoryBlock, formal.name(), formal.type());
        context.registerSymbol(formal.name(), variable);
        return variable;
    }

    private void bind(Variable variable, Value value) {
        variable.write(value.value());
    }
}
