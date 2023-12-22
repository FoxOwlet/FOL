package com.foxowlet.fol.interpreter.predefined.function;

import com.foxowlet.fol.ast.Assignment;
import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.ast.FunctionCall;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.interpreter.AbstractInterpreterTest;
import com.foxowlet.fol.interpreter.model.type.UnitType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.foxowlet.fol.interpreter.AstUtils.*;
import static com.foxowlet.fol.interpreter.assertion.AssertionUtils.assertFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintTest extends AbstractInterpreterTest {
    private PrintStream oldStream;
    private ByteArrayOutputStream stream;

    @BeforeEach
    void mockSysOut() {
        oldStream = System.out;
        stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
    }

    @AfterEach
    void restoreSysOut() {
        System.setOut(oldStream);
    }

    @Disabled("TODO: fixme")
    @Test
    void predefinedFunction_shouldBeAssignableIntoVariable() {
        // var p: Int->Unit = print;
        Assignment var = var("p", ftype("Int", "Unit"), new Symbol("print"));

        Object actual = interpret(var);

        assertFunction(actual).hasReturnType(new UnitType());
    }

    @Disabled("TODO: fixme")
    @Test
    void predefinedFunction_shouldBeCallable_whenAssignedIntoVariable() {
        // var p: Int->Unit = print;
        Assignment var = var("p", ftype("Int", "Unit"), new Symbol("print"));
        // p(42)
        FunctionCall call = call(new Symbol("p"), literal(42));
        // { ... }
        Block block = block(var, call);

        interpret(block);

        assertEquals("42", stream.toString());
    }
}
