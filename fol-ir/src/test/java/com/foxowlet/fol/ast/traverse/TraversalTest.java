package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.foxowlet.fol.ast.traverse.TraversalAssertions.*;

public class TraversalTest {
    @Test
    void shouldCombineRules() {
        // Flatten nested blocks (post-order)
        Consumer<Traversal> flattenBlocks = post(node -> switch (node) {
            case Block(NodeSeq(List<Expression> exprs)) ->
                    new Block(new NodeSeq<>(
                            exprs.stream()
                                    .flatMap(expr -> expr instanceof Block block
                                            ? block.exprs().subnodes().stream()
                                            : Stream.of(expr))
                                    .toList()));
            default -> null;
        });
        // Unfold assignments (pre-order continue)
        Consumer<Traversal> unfoldAssignments = preContinue(node -> switch (node) {
            case Assignment(Expression lhs1, Assignment(Expression lhs2, Expression rhs)) ->
                    new Block(NodeSeq.of(new Assignment(lhs2, rhs), new Assignment(lhs1, lhs2)));
            default -> null;
        });
        // {
        //   a = b = c = 42
        //   { {} }
        //   a
        // }
        Block input = new Block(NodeSeq.of(
                new Assignment(new Symbol("a"),
                        new Assignment(new Symbol("b"),
                                new Assignment(new Symbol("c"), new IntLiteral(42)))),
                new Block(NodeSeq.of(new Block(NodeSeq.of()))),
                new Symbol("a")));
        // {
        //   c = 42
        //   b = c
        //   a = b
        //   a
        // }
        Block output = new Block(NodeSeq.of(
                new Assignment(new Symbol("c"), new IntLiteral(42)),
                new Assignment(new Symbol("b"), new Symbol("c")),
                new Assignment(new Symbol("a"), new Symbol("b")),
                new Symbol("a")));

        given(input)
                .when(unfoldAssignments)
                .when(flattenBlocks)
                .then(output);
    }
}
