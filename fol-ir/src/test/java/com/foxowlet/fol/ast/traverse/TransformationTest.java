package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static com.foxowlet.fol.ast.traverse.TraversalAssertions.given;

public class TransformationTest {

    @Test
    void shouldCombineRules() {
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
                .when(new TestTransformation())
                .then(output);
    }

    private static class TestTransformation extends AbstractTransformation {
        @Override
        protected Node post(Node node) {
            // Flatten nested blocks
            return switch (node) {
                case Block(NodeSeq(List<Expression> exprs)) ->
                        new Block(new NodeSeq<>(
                                exprs.stream()
                                        .flatMap(expr -> expr instanceof Block block
                                                ? block.exprs().subnodes().stream()
                                                : Stream.of(expr))
                                        .toList()));
                default -> null;
            };
        }

        @Override
        protected Node preContinue(Node node) {
            // Unfold assignments
            return switch (node) {
                case Assignment(Expression lhs1, Assignment(Expression lhs2, Expression rhs)) ->
                        new Block(NodeSeq.of(new Assignment(lhs2, rhs), new Assignment(lhs1, lhs2)));
                default -> null;
            };
        }
    }
}
