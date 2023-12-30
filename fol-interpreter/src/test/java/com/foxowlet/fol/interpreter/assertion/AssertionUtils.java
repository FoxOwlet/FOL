package com.foxowlet.fol.interpreter.assertion;

import com.foxowlet.fol.interpreter.model.Function;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.type.RefType;
import com.foxowlet.fol.interpreter.model.type.StringType;
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
        RefType refType = assertInstanceOf(RefType.class, type);
        return new StructTypeAssertion(assertInstanceOf(StructType.class, refType.pointedType()));
    }

    public static StructAssertion assertStruct(Object actual) {
        Value value = asValue(actual);
        RefType refType = assertInstanceOf(RefType.class, value.type());
        byte[] struct = assertInstanceOf(byte[].class, value.deref());
        StructType type = assertInstanceOf(StructType.class, refType.pointedType());
        return new StructAssertion(struct, type);
    }

    private static Value asValue(Object actual) {
        return assertInstanceOf(Value.class, actual);
    }

    public static StringAssertion assertString(Object actual) {
        Value value = asValue(actual);
        StringType stringType = assertInstanceOf(StringType.class, value.type());
        String string = stringType.deref(value.value());
        return new StringAssertion(string);
    }
}
