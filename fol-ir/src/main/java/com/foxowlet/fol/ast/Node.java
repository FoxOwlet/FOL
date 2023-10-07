package com.foxowlet.fol.ast;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public sealed interface Node permits Expression, FormalParameter, NodeSeq, Type {

    default List<? extends Node> children() {
        Field[] fields = getClass().getDeclaredFields();
        boolean allNodes = true;
        for (Field field : fields) {
            if (Node.class.isAssignableFrom(field.getType())) {
                if (!allNodes) {
                    throw new IllegalStateException("Node children must be either all nodes or all scalars");
                }
            } else {
                allNodes = false;
            }
        }
        if (!allNodes) {
            return List.of();
        }
        return Arrays.stream(fields)
                .map(this::getFieldValue)
                .map(Node.class::cast)
                .toList();
    }

    private Object getFieldValue(Field f) {
        try {
            f.setAccessible(true);
            return f.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
