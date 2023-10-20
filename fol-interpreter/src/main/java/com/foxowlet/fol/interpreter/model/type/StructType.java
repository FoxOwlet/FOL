package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.*;

import java.util.Iterator;
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
    public Value call(List<Value> actuals, InterpretationContext context) {
        MemoryBlock memoryBlock = context.allocateMemory(size);
        Iterator<Field> fieldsIterator = fields.iterator();
        Iterator<Value> actualsIterator = actuals.iterator();
        // assume same length. It's checked by FunctionCallInterpreter before the call
        while (fieldsIterator.hasNext() && actualsIterator.hasNext()) {
            Field field = fieldsIterator.next();
            byte[] encoded = field.type().encode(actualsIterator.next().value());
            memoryBlock.slice(field.offset(), field.type().size()).write(encoded);
        }
        return new Struct(memoryBlock, this);
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
