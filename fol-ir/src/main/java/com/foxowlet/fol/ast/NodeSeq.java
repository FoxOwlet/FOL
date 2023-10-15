package com.foxowlet.fol.ast;

import java.util.List;

public record NodeSeq<T extends Node>(List<T> subnodes) implements Node {
    @Override
    public List<? extends Node> children() {
        return subnodes;
    }

    @SafeVarargs
    public static <T extends Node> NodeSeq<T> of(T... subnodes) {
        return new NodeSeq<>(List.of(subnodes));
    }
}
