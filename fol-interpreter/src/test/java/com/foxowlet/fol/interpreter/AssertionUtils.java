package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.interpreter.model.Field;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.type.FunctionTypeDescriptor;
import com.foxowlet.fol.interpreter.model.type.StructType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public final class AssertionUtils {
    private AssertionUtils() {}

    public static <T> void assertValue(T expected, Object actual) {
        Value value = assertInstanceOf(Value.class, actual);
        assertEquals(expected, value.value());
    }

    public static void assertFunctionType(Variable function, TypeDescriptor... parts) {
        TypeDescriptor type = function.type();
        int lastIndex = parts.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            FunctionTypeDescriptor ftype = assertInstanceOf(FunctionTypeDescriptor.class, type);
            TypeDescriptor part = parts[i];
            assertEquals(part, ftype.argumentType());
            type = ftype.returnType();
        }
        assertEquals(parts[lastIndex], type);
    }

    public static void assertStructType(Object type, String name, Field... fields) {
        StructType struct = assertInstanceOf(StructType.class, type);
        assertEquals(name, struct.name());
        assertEquals(Arrays.asList(fields), struct.fields());
    }

    public static void assertStruct(Object actual, byte[]... fields) {
        Value value = assertInstanceOf(Value.class, actual);
        byte[] struct = assertInstanceOf(byte[].class, value.value());
        int offset = 0;
        for (byte[] field : fields) {
            assertArrayEquals(field, Arrays.copyOfRange(struct, offset, offset + field.length));
            offset += field.length;
        }
    }
}
