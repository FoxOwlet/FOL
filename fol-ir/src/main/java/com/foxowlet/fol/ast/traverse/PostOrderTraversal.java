package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.*;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.Function;

public final class PostOrderTraversal implements Traversal {
    private final Function<? super Node, ? extends Node> rule;

    public PostOrderTraversal(Function<? super Node, ? extends Node> rule) {
        this.rule = rule;
    }

    @Override
    public Node traverse(Node node) {
        List<Node> newChildren = node.children().stream()
                .map(this::traverse)
                .toList();
        Node newNode = tryReplaceChildren(node, newChildren);
        Node result = rule.apply(newNode);
        return result == null ? newNode : result;
    }

    private Node tryReplaceChildren(Node node, List<Node> newChildren) {
        if (node.children().equals(newChildren)) {
            return node;
        }
        try {
            return replaceChildren(node, newChildren);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Node replaceChildren(Node node, List<Node> newChildren) throws Exception {
        if (newChildren.isEmpty()) {
            return node;
        }
        Constructor<?> constructor = node.getClass().getConstructors()[0];
        Object[] args;
        if (node instanceof NodeSeq) {
            args = new Object[]{newChildren};
        } else {
            args = newChildren.toArray();
        }
        return (Node) constructor.newInstance(args);
    }
}
