package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.interpreter.model.Function;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.type.FunctionTypeDescriptor;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AbstractInterpreterTest {
    private final Interpreter interpreter;

    public AbstractInterpreterTest() {
        this.interpreter = new Interpreter(new Emulator(), getInterpreterConfiguration());
    }

    protected InterpreterConfiguration getInterpreterConfiguration() {
        return new InterpreterConfiguration();
    }

    protected final Object interpret(Expression expression) {
        return interpreter.interpret(expression);
    }

    protected static <T> void assertValue(T expected, Object value) {
        assertInstanceOf(Value.class, value);
        assertEquals(expected, ((Value) value).value());
    }

    protected static void assertFunctionType(Variable function, TypeDescriptor... parts) {
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
}
