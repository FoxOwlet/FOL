package com.foxowlet.fol.interpreter.model;

import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;

public record Field(int offset, String name, TypeDescriptor type) {
}
