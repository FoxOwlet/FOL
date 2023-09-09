package com.foxowlet.fol.ast;

import java.util.List;

public record Lambda(List<FormalParameter> params, Block body) implements Expression {
}
