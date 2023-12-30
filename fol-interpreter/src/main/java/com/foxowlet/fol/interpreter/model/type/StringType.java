package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.emulator.memory.Memory;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.memory.MemoryBlock;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class StringType implements TypeDescriptor {
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    private final TypeDescriptor pointerType;
    private final TypeDescriptor sizeType;
    private final Memory memory;

    public StringType(Memory memory) {
        this.memory = memory;
        this.pointerType = new IntType();
        this.sizeType = new IntType();
    }

    @Override
    public String name() {
        return "String";
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

    public int encode(InterpretationContext context, String value) {
        byte[] bytes = value.getBytes(CHARSET);
        MemoryBlock sizeBlock = context.allocateMemory(sizeType.size());
        sizeBlock.write(sizeType.encode(bytes.length));
        MemoryBlock contentBlock = context.allocateMemory(bytes.length);
        contentBlock.write(bytes);
        return sizeBlock.address();
    }

    public String deref(Object value) {
        int address = ReflectionUtils.as(value, Integer.class);
        byte[] rawSize = new MemoryBlock(memory, address, sizeType.size()).read();
        int size = ReflectionUtils.as(sizeType.decode(rawSize), Integer.class);
        byte[] content = new MemoryBlock(memory, address + sizeType.size(), size).read();
        return new String(content, CHARSET);
    }

    @Override
    public boolean isCompatibleWith(TypeDescriptor other) {
        return equals(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return obj instanceof StringType;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
