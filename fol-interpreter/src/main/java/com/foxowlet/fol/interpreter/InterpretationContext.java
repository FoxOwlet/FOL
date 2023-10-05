package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.interpreter.exception.TypeException;
import com.foxowlet.fol.interpreter.expression.context.ExpressionContext;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.MemoryBlock;

import java.util.function.Supplier;

public interface InterpretationContext {
    MemoryBlock allocateMemory(int amount);

    void enterScope();

    void exitScope();

    void registerSymbol(String name, Object value);

    Object lookupSymbol(String name);

    Object interpret(Expression expression);

    default <T> T interpret(Expression expression, Class<T> tClass) {
        return ReflectionUtils.as(interpret(expression), tClass);
    }

    default <T> T interpret(Expression expression, Class<T> tClass, String errorMessage) {
        return ReflectionUtils.as(interpret(expression), tClass, TypeException.prepare(errorMessage));
    }

    int allocateFunction();

    ExpressionContext expressionContext();

    default <T extends ExpressionContext> T expressionContext(Class<T> tClass) {
        return ReflectionUtils.as(expressionContext(), tClass);
    }

    <T> T withExpressionContext(ExpressionContext ctx, Supplier<T> action);
}
