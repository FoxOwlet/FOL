package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.Node;

public interface Transformation {
    Node apply(Node node);
}
