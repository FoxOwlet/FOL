package com.foxowlet.fol.interpreter.assertion;

import com.foxowlet.fol.interpreter.model.Function;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.StructType;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public final class AssertionUtils {
    private AssertionUtils() {}

    public static ValueAssertion assertValue(Object actual) {
        return new ValueAssertion(asValue(actual));
    }

    public static FunctionAssertion assertFunction(Object actual) {
        Function function = assertInstanceOf(Function.class, asValue(actual).value());
        return new FunctionAssertion(function);
    }

    public static StructTypeAssertion assertStructType(Object type) {
        return new StructTypeAssertion(assertInstanceOf(StructType.class, type));
    }

    public static StructAssertion assertStruct(Object actual) {
        Value value = asValue(actual);
        byte[] struct = assertInstanceOf(byte[].class, value.value());
        StructType type = assertInstanceOf(StructType.class, value.type());
        return new StructAssertion(struct, type);
    }

    private static Value asValue(Object actual) {
        return assertInstanceOf(Value.class, actual);
    }
}
