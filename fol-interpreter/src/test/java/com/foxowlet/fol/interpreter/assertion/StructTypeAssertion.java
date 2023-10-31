package com.foxowlet.fol.interpreter.assertion;

import com.foxowlet.fol.interpreter.model.Field;
import com.foxowlet.fol.interpreter.model.type.StructType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class StructTypeAssertion {
    private final StructAssertion parent;
    private final StructType type;

    StructTypeAssertion(StructType type) {
        this(null, type);
    }

    StructTypeAssertion(StructAssertion parent, StructType type) {
        this.parent = parent;
        this.type = type;
    }

    public StructTypeAssertion hasName(String name) {
        assertEquals(name, type.name());
        return this;
    }

    public StructTypeAssertion hasField(String name, TypeDescriptor type) {
        Optional<Field> field = this.type.fields().stream()
                .filter(f -> f.name().equals(name))
                .findAny();
        assertTrue(field.isPresent(), "No field with name %s in type %s".formatted(name, this.type));
        assertEquals(type, field.get().type());
        return this;
    }

    public StructTypeAssertion hasField(int offset, String name, TypeDescriptor type) {
        assertTrue(this.type.fields().contains(new Field(offset, name, type)));
        return this;
    }

    public StructAssertion end() {
        if (parent == null) {
            throw new IllegalStateException("%s was used ouf of %s context"
                    .formatted(getClass().getSimpleName(), StructAssertion.class.getSimpleName()));
        }
        return parent;
    }
}
