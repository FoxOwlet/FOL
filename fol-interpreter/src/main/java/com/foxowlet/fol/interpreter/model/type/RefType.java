package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.emulator.memory.Memory;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.TypeException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Callable;
import com.foxowlet.fol.interpreter.model.FunctionParameter;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.memory.MemoryBlock;
import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;

import java.util.List;
import java.util.Objects;

public final class RefType implements TypeDescriptor, Callable {
    private final TypeDescriptor pointerType;
    private final TypeDescriptor pointedType;
    private final Memory memory;

    public RefType(Memory memory, TypeDescriptor pointedType) {
        this.memory = memory;
        this.pointerType = new IntType();
        this.pointedType = pointedType;
    }

    @Override
    public String name() {
        return "Ref[%s]".formatted(pointedType.name());
    }

    @Override
    public int size() {
        return pointerType.size();
    }

    @Override
    public byte[] encode(Object value) {
        return pointerType.encode(value);
    }

    @Override
    public Object decode(byte[] data) {
        return pointerType.decode(data);
    }

    @Override
    public Value call(List<Value> actuals, InterpretationContext context) {
        return asCallable().call(actuals, context);
    }

    @Override
    public List<FunctionParameter> params() {
        return asCallable().params();
    }

    public TypeDescriptor pointedType() {
        return pointedType;
    }

    public Object deref(Object value) {
        byte[] bytes = pointedMemory(value).read();
        return pointedType.decode(bytes);
    }

    public MemoryLocation pointedMemory(Object value) {
        int address = ReflectionUtils.as(value, Integer.class);
        return new MemoryBlock(memory, address, pointedType.size());
    }

    private Callable asCallable() {
        return ReflectionUtils.as(pointedType, Callable.class, TypeException::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefType refType = (RefType) o;
        return Objects.equals(pointerType, refType.pointerType) && Objects.equals(pointedType, refType.pointedType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointerType, pointedType);
    }
}
