package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.Assignment;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.IncompatibleTypeException;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.Variable;

public class AssignmentInterpreter implements ExpressionInterpreter<Assignment> {
    @Override
    public Object interpret(Assignment expression, InterpretationContext context) {
        Value val = context.interpret(expression.rhs(), Value.class,
                "Invalid assignment source");
        Variable var = context.interpret(expression.lhs(), Variable.class,
                "Invalid assigment target");
        if (!var.type().isCompatibleWith(val.type())) {
            throw new IncompatibleTypeException(val, var.type());
        }
        var.write(val.value());
        return var;
    }
}
