package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.emulator.memory.Memory;
import com.foxowlet.fol.interpreter.exception.TypeException;
import com.foxowlet.fol.interpreter.exception.UndefinedSymbolException;
import com.foxowlet.fol.interpreter.expression.*;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        interpreterMap.put(ReflectionUtils.expressionClass(interpreter), interpreter);
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
            return Optional.of(variableMap.get(name))
                    .orElseThrow(UndefinedSymbolException.prepare(name));
        }

        public Object interpret(Expression expression) {
            return Interpreter.this.interpret(expression, this);
        }

        public <T> T interpret(Expression expression, Class<T> tClass) {
            return ReflectionUtils.as(interpret(expression), tClass);
        }

        public <T> T interpret(Expression expression, Class<T> tClass, String errorMessage) {
            return ReflectionUtils.as(interpret(expression), tClass, TypeException.prepare(errorMessage));
        }
    }
}

