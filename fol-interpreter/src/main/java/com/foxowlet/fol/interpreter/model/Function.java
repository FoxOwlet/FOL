package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.internal.BiIterator;
import com.foxowlet.fol.interpreter.internal.TypeUtils;
import com.foxowlet.fol.interpreter.model.memory.DummyMemoryLocation;
import com.foxowlet.fol.interpreter.model.memory.MemoryBlock;
import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.List;

public final class Function implements Value, Callable {
    private final int id;
    private final List<FunctionParameter> params;
    private final TypeDescriptor returnType;
    private final TypeDescriptor type;
    private final Block body;

    public Function(int id, List<FunctionParameter> params, TypeDescriptor returnType, Block body, InterpretationContext context) {
        this.id = id;
        this.params = params;
        this.returnType = returnType;
        this.type = TypeUtils.makeFunctionTypeDescriptor(params, returnType, context);
        this.body = body;
    }

    public int id() {
        return id;
    }

    @Override
    public List<FunctionParameter> params() {
        return params;
    }

    public Block body() {
        return body;
    }

    public TypeDescriptor returnType() {
        return returnType;
    }

    @Override
    public Object value() {
        return this;
    }

    @Override
    public TypeDescriptor type() {
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
