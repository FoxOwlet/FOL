package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.FieldAccess;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.exception.UnresolvedFieldException;
import com.foxowlet.fol.interpreter.internal.ReflectionUtils;
import com.foxowlet.fol.interpreter.model.Field;
import com.foxowlet.fol.interpreter.model.Value;
import com.foxowlet.fol.interpreter.model.Variable;
import com.foxowlet.fol.interpreter.model.memory.MemoryLocation;
import com.foxowlet.fol.interpreter.model.type.RefType;
import com.foxowlet.fol.interpreter.model.type.StructType;

public class FieldAccessInterpreter implements ExpressionInterpreter<FieldAccess> {
    @Override
    public Value interpret(FieldAccess expression, InterpretationContext context) {
        Value target = context.interpret(expression.target(), Value.class);
        RefType refType = ReflectionUtils.as(target.type(), RefType.class);
        StructType type = ReflectionUtils.as(refType.pointedType(), StructType.class);
        String fieldName = expression.field().name();
        Field field = type.getField(fieldName)
                .orElseThrow(UnresolvedFieldException.prepare(fieldName, type.name()));
        MemoryLocation pointedMemory = refType.pointedMemory(target.value());
        MemoryLocation slice = pointedMemory.slice(field.offset(), field.type().size());
        return new Variable(slice, field.name(), field.type());
    }
}
