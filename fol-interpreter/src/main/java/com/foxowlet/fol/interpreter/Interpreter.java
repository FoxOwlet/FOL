package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.emulator.Emulator;
import com.foxowlet.fol.emulator.memory.Memory;
import com.foxowlet.fol.interpreter.expression.*;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.memory.MemoryBlock;
import com.foxowlet.fol.interpreter.model.type.RefType;
import com.foxowlet.fol.interpreter.model.type.StringType;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;
import com.foxowlet.fol.interpreter.scope.LookupScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    private static final Map<Class<?>, ExpressionInterpreter<?>> interpreterMap = new HashMap<>();

    static {
        List.of(
                new FileInterpreter(),
                new VarDeclInterpreter(),
                new SymbolInterpreter(),
                new AssignmentInterpreter(),
                new BlockInterpreter(),
                new IntLiteralInterpreter(),
                new ByteLiteralInterpreter(),
                new LongLiteralInterpreter(),
                new StringLiteralInterpreter(),
                new AdditionInterpreter(),
                new SubtractionInterpreter(),
                new MultiplicationInterpreter(),
                new DivisionInterpreter(),
                new LambdaInterpreter(),
                new FunctionCallInterpreter(),
                new FunctionDeclInterpreter(),
                new StructDeclInterpreter(),
                new FieldAccessInterpreter(),
                new IfInterpreter(),
                new EqualsInterpreter()
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

    public Session startSession() {
        InterpretationContext context = new Context();
        config.getPredefinedProcessor().preprocess(context);
        return new Session(context);
    }

    public Value interpret(Expression expression) {
        return startSession().interpret(expression);
    }

    private Value interpret(Expression expression, InterpretationContext context) {
        ExpressionInterpreter<?> interpreter = interpreterMap.get(expression.getClass());
        if (interpreter == null) {
            throw new IllegalStateException("No interpreter defined for " + expression.getClass().getName());
        }
        return interpreter.interpretRaw(expression, context);
    }

    private static void register(ExpressionInterpreter<?> interpreter) {
        Class<?> exprClass = ReflectionUtils.expressionClass(interpreter);
        interpreterMap.put(exprClass, interpreter);
    }

    public final class Context implements InterpretationContext {
        private final LookupScope lookupScope;
        private int functionId;

        private Context() {
            this.lookupScope = new LookupScope();
            this.functionId = 0;
        }

        @Override
        public MemoryBlock allocateMemory(int amount) {
            int address = offset;
            offset += amount;
            return new MemoryBlock(memory, address, amount);
        }

        @Override
        public void enterScope() {
            lookupScope.enterScope();
        }

        @Override
        public void exitScope() {
            lookupScope.exitScope();
        }

        @Override
        public void registerSymbol(String name, Object value) {
            lookupScope.registerSymbol(name, value);
        }

        @Override
        public Object lookupSymbol(String name) {
            return lookupScope.lookupSymbol(name);
        }

        @Override
        public Value interpret(Expression expression) {
            return Interpreter.this.interpret(expression, this);
        }

        @Override
        public int allocateFunction() {
            return ++functionId;
        }

        @Override
        public RefType makeRef(TypeDescriptor type) {
            return new RefType(memory, type);
        }

        @Override
        public StringType makeString() {
            return new StringType(memory);
        }
    }

    public static class Session {
        private final InterpretationContext context;

        private Session(InterpretationContext context) {
            this.context = context;
        }

        public Value interpret(Expression expression) {
            return context.interpret(expression);
        }
    }
}
