package com.foxowlet.fol.interpreter.predefined.function;

import com.foxowlet.fol.interpreter.model.Callable;
import com.foxowlet.fol.interpreter.model.Value;

public interface PredefinedFunction extends Value, Callable {
    String name();
}
