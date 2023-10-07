package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.foxowlet.fol.ast.traverse.MatchUtils.match;
import static com.foxowlet.fol.ast.traverse.MatchUtils.matcher;
import static com.foxowlet.fol.ast.traverse.PostOrderTraversalTest.PostOrderTraversalAssertions.given;

@SuppressWarnings("DuplicateExpressions")
class PostOrderTraversalTest {
    @Test
    void shouldDoNothing_whenNoRuleGiven() {
        Block node = new Block(NodeSeq.of(new Symbol("a"), new Symbol("b")));
        given(node)
                .when(Function.identity())
                .then(node);
    }

    @Test
    void shouldUpdateNode() {
        given(new Symbol("a"))
                .when(node -> new Symbol("b"))
                .then(new Symbol("b"));
    }

    @Test
    void shouldUpdateInnerNodes() {
        given(new VarDecl(new Symbol("a"),
                new ScalarType(new Symbol("Int"))))
                .when(node -> switch (node) {
                    case Symbol __ -> new Symbol("foo");
                    default -> null;
                })
                .then(new VarDecl(new Symbol("foo"),
                        new ScalarType(new Symbol("foo"))));
    }

    @Test
    void shouldHandleNodeSeq() {
        given(new Block(NodeSeq.of(
                new VarDecl(new Symbol("a"),
                        new ScalarType(new Symbol("Int"))),
                new Symbol("a"))))
                .when(node -> switch (node) {
                    case Symbol(String name) ->
                            match(name, "a", new Symbol("b"));
                    default -> null;
                })
                .then(new Block(NodeSeq.of(
                        new VarDecl(new Symbol("b"),
                                new ScalarType(new Symbol("Int"))),
                        new Symbol("b"))));
    }

    @Test
    void shouldApplyMultipleRules() {
        given(new VarDecl(new Symbol("a"),
                new FunctionType(new ScalarType(new Symbol("Int")),
                        new FunctionType(new ScalarType(new Symbol("Long")),
                                new ScalarType(new Symbol("Unit"))))))
                .when(node -> switch (node) {
                    case Symbol(String name) ->
                            match(name, "a", new Symbol("b"));
                    case FunctionType(ScalarType __, FunctionType ret) ->
                            ret;
                    default -> null;
                })
                .then(new VarDecl(new Symbol("b"),
                        new FunctionType(new ScalarType(new Symbol("Long")),
                                new ScalarType(new Symbol("Unit")))));
    }

    @Test
    void shouldApplyRulesInPostOrder() {
        given(new Block(NodeSeq.of(
                new VarDecl(new Symbol("a"), new ScalarType(new Symbol("Int"))),
                new VarDecl(new Symbol("b"), new ScalarType(new Symbol("Unit"))))))
                .when(node -> switch (node) {
                    case Symbol(String name) ->
                            matcher()
                                    .cond(name, "b", new Symbol("test"))
                                    .otherwise(name, "Unit", new Symbol("Test"));
                    case VarDecl(Symbol(String var), ScalarType(Symbol(String type))) ->
                            matcher()
                                    .match(var, "test")
                                    .match(type, "Test")
                                    .then(new Symbol("test"));
                    default ->  null;
                })
                .then(new Block(NodeSeq.of(
                        new VarDecl(new Symbol("a"), new ScalarType(new Symbol("Int"))),
                        new Symbol("test"))));
    }

    @Test
    void shouldThrowException_whenIncompatibleTypes() {
        given(new VarDecl(new Symbol("a"), new ScalarType(new Symbol("Int"))))
                .when(node -> switch (node) {
                    case Symbol(String name) ->
                            match(name, "a", new IntLiteral(42));
                    default -> null;
                })
                .thenFail();
    }

    static final class PostOrderTraversalAssertions {
        private final Node node;
        private Function<? super Node, ? extends Node> rule;

        private PostOrderTraversalAssertions(Node node) {
            this.node = node;
            this.rule = Function.identity();
        }

        public static PostOrderTraversalAssertions given(Node node) {
            return new PostOrderTraversalAssertions(node);
        }

        public PostOrderTraversalAssertions when(Function<? super Node, ? extends Node> rule) {
            this.rule = rule;
            return this;
        }

        public void then(Node expected) {
            PostOrderTraversal traversal = new PostOrderTraversal(rule);
            Assertions.assertEquals(expected, traversal.traverse(node));
        }

        public void thenFail() {
            PostOrderTraversal traversal = new PostOrderTraversal(rule);
            Assertions.assertThrows(Exception.class, () -> traversal.traverse(node));
        }
    }

}