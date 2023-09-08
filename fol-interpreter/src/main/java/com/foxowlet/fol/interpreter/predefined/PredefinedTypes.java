package com.foxowlet.fol.interpreter.predefined;

import com.foxowlet.fol.interpreter.ContextPreprocessor;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.type.ByteType;
import com.foxowlet.fol.interpreter.model.type.IntType;
import com.foxowlet.fol.interpreter.model.type.LongType;
import com.foxowlet.fol.interpreter.model.type.Type;

import java.util.List;

public class PredefinedTypes implements ContextPreprocessor {
    private final List<Type> predefinedTypes;

    public PredefinedTypes() {
        predefinedTypes = List.of(
                new ByteType(),
                new IntType(),
                new LongType()
        );
    }

    @Override
    public void preprocess(InterpretationContext context) {
        predefinedTypes.forEach(type -> context.registerSymbol(type.name(), type));
    }
}
