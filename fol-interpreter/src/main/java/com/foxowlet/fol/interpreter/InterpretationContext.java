package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.exception.TypeException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.MemoryBlock;

public interface InterpretationContext {
    MemoryBlock allocateMemory(int amount);

    void registerSymbol(String name, Object value);

    Object lookupSymbol(String name);

    Object interpret(Expression expression);

    default <T> T interpret(Expression expression, Class<T> tClass) {
        return ReflectionUtils.as(interpret(expression), tClass);
    }

    default <T> T interpret(Expression expression, Class<T> tClass, String errorMessage) {
        return ReflectionUtils.as(interpret(expression), tClass, TypeException.prepare(errorMessage));
    }
}
