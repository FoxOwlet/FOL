package com.foxowlet.fol.interpreter.predefined;

import com.foxowlet.fol.interpreter.ContextPreprocessor;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.predefined.function.PredefinedFunction;
import com.foxowlet.fol.interpreter.predefined.function.Print;

import java.util.List;
import java.util.function.Function;

public class PredefinedFunctions implements ContextPreprocessor {
    private final List<Function<InterpretationContext, PredefinedFunction>> functions;

    public PredefinedFunctions() {
        functions = List.of(Print::new);
    }

    @Override
    public void preprocess(InterpretationContext context) {
        functions.forEach(fn -> registerFunction(context, fn));
    }

    private void registerFunction(InterpretationContext context,
                                  Function<InterpretationContext, PredefinedFunction> constructor) {
        PredefinedFunction function = constructor.apply(context);
        context.registerSymbol(function.name(), function);
    }
}
