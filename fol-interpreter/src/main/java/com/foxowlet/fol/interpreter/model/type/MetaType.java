package com.foxowlet.fol.interpreter.model.type;

public class MetaType implements TypeDescriptor {
    private final TypeDescriptor type;

    public MetaType(TypeDescriptor type) {
        this.type = type;
    }

    @Override
    public String name() {
        return String.format("Type[%s]", type.name());
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public byte[] encode(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object decode(byte[] data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass()) && type.equals(((MetaType) obj).type);
    }
}
