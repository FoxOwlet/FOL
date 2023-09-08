package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.emulator.memory.Memory;
import com.foxowlet.fol.interpreter.exception.DuplicateSymbolException;
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

    private final InterpreterConfiguration config;
    private final Memory memory;
    private int offset;

    public Interpreter(Emulator emulator) {
        this(emulator, new InterpreterConfiguration());
    }

    public Interpreter(Emulator emulator, InterpreterConfiguration config) {
        this.config = config;
        this.memory = emulator.allocate(config.getMemoryLimit());
        this.offset = 0;
    }

    public Object interpret(Expression expression) {
        InterpretationContext context = new Context();
        config.getPredefinedProcessor().preprocess(context);
        return interpret(expression, context);
    }

    private Object interpret(Expression expression, InterpretationContext context) {
        ExpressionInterpreter<? extends Expression> interpreter = interpreterMap.get(expression.getClass());
        if (interpreter == null) {
            throw new IllegalStateException("No interpreter defined for " + expression.getClass().getName());
        }
        return interpreter.interpretRaw(expression, context);
    }

    private static void register(ExpressionInterpreter<? extends Expression> interpreter) {
        interpreterMap.put(ReflectionUtils.expressionClass(interpreter), interpreter);
    }

    public final class Context implements InterpretationContext {
        private final Map<String, Object> symbolMap;

        private Context() {
            this.symbolMap = new HashMap<>();
        }

        @Override
        public MemoryBlock allocateMemory(int amount) {
            int address = offset;
            offset += amount;
            return new MemoryBlock(memory, address, amount);
        }

        @Override
        public void registerSymbol(String name, Object value) {
            if (symbolMap.containsKey(name)) {
                throw new DuplicateSymbolException(name);
            }
            symbolMap.put(name, value);
        }

        @Override
        public Object lookupSymbol(String name) {
            return Optional.ofNullable(symbolMap.get(name))
                    .orElseThrow(UndefinedSymbolException.prepare(name));
        }

        @Override
        public Object interpret(Expression expression) {
            return Interpreter.this.interpret(expression, this);
        }
    }
}
