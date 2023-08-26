package com.foxowlet.fol.interpreter;

import com.foxowlet.fol.ast.Addition;
import com.foxowlet.fol.ast.Assignment;
import com.foxowlet.fol.ast.Expression;
import com.foxowlet.fol.ast.IntLiteral;
import com.foxowlet.fol.ast.Symbol;
import com.foxowlet.fol.ast.VarDecl;
import com.foxowlet.fol.ast.Block;
import com.foxowlet.fol.emulator.Memory;
import com.foxowlet.fol.interpreter.model.Container;
import com.foxowlet.fol.interpreter.model.IntType;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.Variable;

import java.util.HashMap;
import java.util.Map;

public class Interpreter {
    private final Map<String, Variable> variableMap = new HashMap<>();
    private int offset = 0;

    public Object interpret(Memory memory, Expression expression) {
        switch (expression) {
            case VarDecl(Symbol(String name), String type) -> {
                if (!type.equals("Int")) {
                    throw new IllegalStateException("Unsupported type " + type);
                }
                IntType varType = new IntType();
                Variable variable = new Variable(memory, offset, name, varType);
                variableMap.put(name, variable);
                offset += varType.size();
                return variable;
            }
            case Assignment(Expression lhs, Expression rhs) -> {
                Object target = interpret(memory, lhs);
                if (target instanceof Variable var) {
                    Object value = interpret(memory, rhs);
                    if (value instanceof Value val) {
                       var.write(val.value());
                       return var;
                    }
                    throw new IllegalStateException("Invalid assignment source " + value);
                }
                throw new IllegalStateException("Invalid assignment target " + target);
            }
            case Symbol(String name) -> {
                Variable variable = variableMap.get(name);
                if (variable == null) {
                    throw new IllegalStateException("Undefined variable " + name);
                }
                return variable;
            }
            case IntLiteral(int value) -> {
                return new Container(value);
            }
            case Addition(Expression left, Expression right) -> {
                Object leftObj = interpret(memory, left);
                Object rightObj = interpret(memory, right);
                if (leftObj instanceof Value leftVal) {
                    if (rightObj instanceof Value rightVal) {
                        return new Container((int) leftVal.value() + (int) rightVal.value());
                    }
                    throw new IllegalStateException("Invalid expression value " + leftObj);
                }
                throw new IllegalStateException("Invalid expression value " + rightObj);
            }
            case Block(var exprs) -> {
                Object result = null;
                for (Expression expr : exprs) {
                    result = interpret(memory, expr);
                }
                return result;
            }
        }
    }
}
