package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.*;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("UnusedReturnValue")
public final class Traversal {
    private static final Function<? super Node, ? extends Node> DEFAULT_RULE = __ -> null;
    private Function<? super Node, ? extends Node> preStopRule;
    private Function<? super Node, ? extends Node> preContinueRule;
    private Function<? super Node, ? extends Node> postRule;

    public Traversal() {
        this.preStopRule = DEFAULT_RULE;
        this.preContinueRule = DEFAULT_RULE;
        this.postRule = DEFAULT_RULE;
    }

    public Traversal preStop(Function<? super Node, ? extends Node> rule) {
        preStopRule = rule;
        return this;
    }

    public Traversal preContinue(Function<? super Node, ? extends Node> rule) {
        preContinueRule = rule;
        return this;
    }

    public Traversal post(Function<? super Node, ? extends Node> rule) {
        postRule = rule;
        return this;
    }

    public Node traverse(Node node) {
        Node preStopNode = preStopRule.apply(node);
        if (preStopNode != null) {
            return preStopNode;
        }
        Node preContinueNode = preContinueRule.apply(node);
        if (preContinueNode == null) {
            preContinueNode = node;
        }
        List<Node> newChildren = preContinueNode.children().stream()
                .map(this::traverse)
                .toList();
        Node newNode = tryReplaceChildren(preContinueNode, newChildren);
        Node postNode = postRule.apply(newNode);
        return postNode != null ? postNode : newNode;
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
