package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.BiIterator;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.*;
import com.foxowlet.fol.interpreter.model.memory.MemoryBlock;

import java.util.List;
import java.util.Optional;

public record StructType(String name, int size, List<Field> fields) implements TypeDescriptor, Callable {

    @Override
    public byte[] encode(Object value) {
        return ReflectionUtils.as(value, byte[].class);
    }

    @Override
    public byte[] decode(byte[] data) {
        return data;
    }

    @Override
    public boolean isCompatibleWith(TypeDescriptor other) {
        return equals(other);
    }

    @Override
    public Value call(List<Value> actuals, InterpretationContext context) {
        MemoryBlock memoryBlock = context.allocateMemory(size);
        new BiIterator<>(fields, actuals)
                .forEachRemaining((field, actual) -> assign(memoryBlock, field, actual));
        return new Struct(memoryBlock, this);
    }

    private void assign(MemoryBlock memoryBlock, Field field, Value actual) {
        byte[] encoded = field.type().encode(actual.value());
        memoryBlock.slice(field.offset(), field.type().size()).write(encoded);
    }

    @Override
    public List<FunctionParameter> params() {
        return fields.stream()
                .map(field -> new FunctionParameter(field.name(), field.type()))
                .toList();
    }

    public Optional<Field> getField(String name) {
        return fields.stream()
                .filter(field -> field.name().equals(name))
                .findAny();
    }
}
