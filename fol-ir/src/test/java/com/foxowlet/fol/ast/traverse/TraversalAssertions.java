package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.Node;
import org.junit.jupiter.api.Assertions;

import java.util.function.Consumer;
import java.util.function.Function;

public final class TraversalAssertions {
    private final Node node;
    private final Traversal traversal;

    private TraversalAssertions(Node node) {
        this.node = node;
        this.traversal = new Traversal();
    }

    public static TraversalAssertions given(Node node) {
        return new TraversalAssertions(node);
    }

    public TraversalAssertions when(Consumer<Traversal> ruleFn) {
        ruleFn.accept(traversal);
        return this;
    }

    public TraversalAssertions when(AbstractTransformation transformation) {
        return when(preStop(transformation::preStop))
                .when(preContinue(transformation::preContinue))
                .when(post(transformation::post));
    }

    public TraversalAssertions when(Function<? super Node, ? extends Node> rule) {
        return when(post(rule));
    }

    public void then(Node expected) {
        Assertions.assertEquals(expected, traversal.traverse(node));
    }

    public void thenFail() {
        Assertions.assertThrows(Exception.class, () -> traversal.traverse(node));
    }

    public static Consumer<Traversal> preStop(Function<? super Node, ? extends Node> rule) {
        return traversal -> traversal.preStop(rule);
    }

    public static Consumer<Traversal> preContinue(Function<? super Node, ? extends Node> rule) {
        return traversal -> traversal.preContinue(rule);
    }

    public static Consumer<Traversal> post(Function<? super Node, ? extends Node> rule) {
        return traversal -> traversal.post(rule);
    }
}
