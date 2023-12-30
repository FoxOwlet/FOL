package com.foxowlet.fol.interpreter.predefined;

import com.foxowlet.fol.interpreter.ContextPreprocessor;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.type.*;

import java.util.List;

final class PredefinedTypes implements ContextPreprocessor {
    private final List<TypeDescriptor> predefinedTypes;

    public PredefinedTypes() {
        predefinedTypes = List.of(
                new ByteType(),
                new IntType(),
                new LongType(),
                new UnitType(),
                new BooleanType()
        );
    }

    @Override
    public void preprocess(InterpretationContext context) {
        predefinedTypes.forEach(type -> context.registerSymbol(type.name(), type));
    }
}
