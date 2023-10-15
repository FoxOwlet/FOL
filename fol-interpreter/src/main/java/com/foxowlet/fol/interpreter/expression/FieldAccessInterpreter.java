package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.FieldAccess;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.UnresolvedFieldException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Field;
import com.foxowlet.fol.interpreter.model.MemoryLocation;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.type.StructType;

public class FieldAccessInterpreter implements ExpressionInterpreter<FieldAccess> {
    @Override
    public Object interpret(FieldAccess expression, InterpretationContext context) {
        Value target = context.interpret(expression.target(), Value.class);
        StructType type = ReflectionUtils.as(target.type(), StructType.class);
        String fieldName = expression.field().name();
        Field field = type.getField(fieldName)
                .orElseThrow(UnresolvedFieldException.prepare(fieldName, type.name()));
        MemoryLocation slice = target.memory().slice(field.offset(), field.type().size());
        return new Variable(slice, field.name(), field.type());
    }
}
