package com.foxowlet.fol.ast;

import java.util.List;

public record FunctionCall(Expression target, List<Expression> arguments) implements Expression {
}
