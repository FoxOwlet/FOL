package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.emulator.memory.Memory;
import com.foxowlet.fol.interpreter.expression.*;
import com.foxowlet.fol.interpreter.model.*;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    private static final Map<Class<?>, ExpressionInterpreter<? extends Expression>> interpreterMap = new HashMap<>();

    static {
        List.of(
                new VarDeclInterpreter(),
                new SymbolInterpreter(),
                new AssignmentInterpreter(),
                new BlockInterpreter(),
                new IntLiteralInterpreter(),
                new AdditionInterpreter()
        ).forEach(Interpreter::register);
    }

    private final Memory memory;
    private int offset;

    public Interpreter(Emulator emulator, int memoryLimit) {
        memory = emulator.allocate(memoryLimit);
        offset = 0;
    }

    public Object interpret(Expression expression) {
        return interpret(expression, new Context());
    }

    private Object interpret(Expression expression, Context context) {
        ExpressionInterpreter<? extends Expression> interpreter = interpreterMap.get(expression.getClass());
        if (interpreter == null) {
            throw new IllegalStateException("No interpreter defined for " + expression.getClass().getName());
        }
        return interpreter.interpretRaw(expression, context);
    }

    private static void register(ExpressionInterpreter<? extends Expression> interpreter) {
        if (interpreter.getClass().getGenericInterfaces()[0] instanceof ParameterizedType type) {
            String typeName = type.getActualTypeArguments()[0].getTypeName();
            try {
                interpreterMap.put(Class.forName(typeName), interpreter);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        } else {
            throw new IllegalStateException("Can't infer type bound for " + interpreter);
        }
    }

    public final class Context {
        private final Map<String, Variable> variableMap;

        private Context() {
            this.variableMap = new HashMap<>();
        }

        public int allocateMemory(int amount) {
            int address = offset;
            offset += amount;
            return address;
        }

        public Variable makeVariable(String name, Type type) {
            Variable variable = new Variable(memory, allocateMemory(type.size()), name, type);
            variableMap.put(name, variable);
            return variable;
        }

        public Variable lookupVariable(String name) {
            Variable variable = variableMap.get(name);
            if (variable == null) {
                throw new IllegalStateException("Undefined variable " + name);
            }
            return variable;
        }

        public Object interpret(Expression expression) {
            return Interpreter.this.interpret(expression, this);
        }

        public <T> T interpret(Expression expression, Class<T> tClass) {
            return tClass.cast(interpret(expression));
        }

        public <T> T interpret(Expression expression, Class<T> tClass, String errorMessage) {
            Object obj = interpret(expression);
            if (tClass.isInstance(obj)) {
                return tClass.cast(obj);
            }
            throw new IllegalStateException(errorMessage + " " + obj);
        }
    }
}

