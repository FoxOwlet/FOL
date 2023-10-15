package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.Node;

public abstract class AbstractTransformation implements Transformation {

    protected Node preStop(Node node) {
        return null;
    }

    protected Node preContinue(Node node) {
        return null;
    }

    protected Node post(Node node) {
        return null;
    }

    @Override
    public final Node apply(Node node) {
        return new Traversal()
                .preStop(this::preStop)
                .preContinue(this::preContinue)
                .post(this::post)
                .traverse(node);
    }
}
