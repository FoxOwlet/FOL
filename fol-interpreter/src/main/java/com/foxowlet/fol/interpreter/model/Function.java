package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.Iterator;
import java.util.List;

public record Function(int id, List<FunctionParameter> params, Block body) implements Value, Callable {
    @Override
    public Object value() {
        return this;
    }

    @Override
    public TypeDescriptor type() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MemoryLocation memory() {
        return new DummyMemoryLocation();
    }

    @Override
    public Value call(List<Value> actuals, InterpretationContext context) {
        bindParams(params(), actuals, context);
        return context.interpret(body(), Value.class);
    }

    private void bindParams(List<FunctionParameter> params,
                            List<Value> arguments,
                            InterpretationContext context) {
        Iterator<FunctionParameter> paramsIterator = params.iterator();
        Iterator<Value> argumentsIterator = arguments.iterator();
        // assume same length. It's checked by FunctionCallInterpreter before the call
        while (paramsIterator.hasNext() && argumentsIterator.hasNext()) {
            Variable formal = makeParameter(paramsIterator.next(), context);
            bind(formal, argumentsIterator.next());
        }
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
