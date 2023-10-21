package com.foxowlet.fol.interpreter.internal;

import java.util.Iterator;
import java.util.function.BiConsumer;

public final class BiIterator<T, U> {
    private final Iterator<T> firstIterator;
    private final Iterator<U> secondIterator;

    public BiIterator(Iterable<T> first, Iterable<U> second) {
        firstIterator = first.iterator();
        secondIterator = second.iterator();
    }

    public boolean hasNext() {
        return firstIterator.hasNext() && secondIterator.hasNext();
    }

    public Pair<T, U> next() {
        return new Pair<>(firstIterator.next(), secondIterator.next());
    }

    public void forEachRemaining(BiConsumer<? super T, ? super U> consumer) {
        while (hasNext()) {
            consumer.accept(firstIterator.next(), secondIterator.next());
        }
    }
}
