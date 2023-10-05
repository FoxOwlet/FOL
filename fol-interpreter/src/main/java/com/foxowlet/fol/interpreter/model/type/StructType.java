package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.*;

import java.util.Iterator;
import java.util.List;

public record StructType(String name, int size, List<FieldDecl> fields) implements TypeDescriptor, Callable {

    @Override
    public byte[] encode(Object value) {
        return ReflectionUtils.as(value, byte[].class);
    }

    @Override
    public Object decode(byte[] data) {
        return data;
    }

    @Override
    public Value call(List<Value> actuals, InterpretationContext context) {
        byte[] bytes = new byte[size];
        Iterator<FieldDecl> fieldsIterator = fields.iterator();
        Iterator<Value> actualsIterator = actuals.iterator();
        // assume same length. It's checked by FunctionCallInterpreter before the call
        while (fieldsIterator.hasNext() && actualsIterator.hasNext()) {
            FieldDecl field = fieldsIterator.next();
            byte[] encoded = field.type().encode(actualsIterator.next().value());
            System.arraycopy(encoded, 0, bytes, field.offset(), field.type().size());
        }
        return new Container(bytes);
    }

    @Override
    public List<FunctionParameter> params() {
        return fields.stream()
                .map(field -> new FunctionParameter(field.name(), field.type()))
                .toList();
    }
}
