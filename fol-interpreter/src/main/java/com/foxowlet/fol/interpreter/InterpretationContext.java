package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.exception.TypeException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.memory.MemoryBlock;
import com.foxowlet.fol.interpreter.model.type.RefType;
import com.foxowlet.fol.interpreter.model.type.StringType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

public interface InterpretationContext {
    MemoryBlock allocateMemory(int amount);

    void enterScope();

    void exitScope();

    void registerSymbol(String name, Object value);

    Object lookupSymbol(String name);

    Value interpret(Expression expression);

    default <T> T interpret(Expression expression, Class<T> tClass) {
        return ReflectionUtils.as(interpret(expression), tClass);
    }

    default <T> T interpret(Expression expression, Class<T> tClass, String errorMessage) {
        return ReflectionUtils.as(interpret(expression), tClass, TypeException.prepare(errorMessage));
    }

    int allocateFunction();

    RefType makeRef(TypeDescriptor type);

    StringType makeString();
}
