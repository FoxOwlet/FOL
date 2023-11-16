package com.foxowlet.fol.interpreter.model.type;

import com.foxowlet.fol.interpreter.exception.TypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BooleanTypeTest {
    private final BooleanType type = new BooleanType();

    @Test
    void encode_shouldReturnZeroArray_whenEncodingFalseValue() {
        byte[] actual = type.encode(false);

        assertArrayEquals(new byte[]{0}, actual);
    }

    @Test
    void encode_shouldReturnNonZeroArray_whenEncodingTrueValue() {
        byte[] actual = type.encode(true);

        assertEquals(1, actual.length);
        assertNotEquals((byte) 0, actual[0]);
    }

    @Test
    void decode_shouldReturnFalse_whenDecodingZeroArray() {
        Object actual = type.decode(new byte[]{0});

        assertEquals(Boolean.FALSE, actual);
    }

    @ParameterizedTest
    @ValueSource(bytes = {1, 2, -1, 100, -128, 127})
    void decode_shouldReturnTrue_whenDecodingNonZeroArray(byte val) {
        Object actual = type.decode(new byte[]{val});

        assertEquals(Boolean.TRUE, actual);
    }

    @Test
    void encode_shouldThrowTypeException_whenInvalidType() {
        assertThrows(TypeException.class, () -> type.encode(42));
    }

    @Test
    void decode_shouldThrowTypeException_whenEmptyArray() {
        assertThrows(TypeException.class, () -> type.decode(new byte[0]));
    }

    @Test
    void decode_shouldThrowTypeException_whenInvalidArraySize() {
        assertThrows(TypeException.class, () -> type.decode(new byte[2]));
    }
}
