package com.foxowlet.fol.interpreter.internal;

import com.foxowlet.fol.interpreter.expression.ExpressionInterpreter;

import java.lang.reflect.ParameterizedType;
import java.util.function.BiFunction;

public final class ReflectionUtils {
    private ReflectionUtils() {}

    public static Class<?> expressionClass(ExpressionInterpreter<?> interpreter) {
        if (interpreter.getClass().getGenericInterfaces()[0] instanceof ParameterizedType type) {
            String typeName = type.getActualTypeArguments()[0].getTypeName();
            return loadClass(typeName);
        } else {
            throw new IllegalStateException("Can't infer type bound for " + interpreter);
        }
    }

    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> T as(Object object, Class<T> tClass) {
        return tClass.cast(object);
    }

    public static <T> T as(Object object,
                           Class<T> tClass,
                           BiFunction<Object, Class<?>, ? extends RuntimeException> exceptionFactory) {
        if (tClass.isInstance(object)) {
            return as(object, tClass);
        }
        throw exceptionFactory.apply(object, tClass);
    }
}
