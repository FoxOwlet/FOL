package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.interpreter.InterpretationContext;

import java.util.Iterator;
import java.util.List;

public record Function(int id, List<FunctionParameter> params, Block body) implements Value, Callable {
    @Override
    public Object value() {
        return this;
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
        while (paramsIterator.hasNext() && argumentsIterator.hasNext()) {
            Variable formal = makeParameter(paramsIterator.next(), context);
            bind(formal, argumentsIterator.next());
        }
        if (paramsIterator.hasNext() || argumentsIterator.hasNext()) {
            // not an InterpreterException as we already checked the lengths before
            // this should be never reached during a normal execution and acts like a guard check
            throw new IllegalStateException("Parameters and arguments length mismatch");
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
