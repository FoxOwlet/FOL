package com.foxowlet.fol.ast;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public record NodeSeq<T extends Node>(List<T> subnodes) implements Node, Iterable<T> {
    @Override
    public List<? extends Node> children() {
        return subnodes;
    }

    @SafeVarargs
    public static <T extends Node> NodeSeq<T> of(T... subnodes) {
        return new NodeSeq<>(List.of(subnodes));
    }

    @Override
    public Iterator<T> iterator() {
        return subnodes.iterator();
    }

    public Stream<T> stream() {
        return subnodes.stream();
    }
}
