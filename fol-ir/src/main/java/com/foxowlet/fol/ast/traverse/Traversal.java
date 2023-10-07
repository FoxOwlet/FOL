package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.Node;

public interface Traversal {
    Node traverse(Node node);
}
