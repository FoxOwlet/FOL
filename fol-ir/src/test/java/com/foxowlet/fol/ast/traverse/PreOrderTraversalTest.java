package com.foxowlet.fol.ast.traverse;

import com.foxowlet.fol.ast.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.foxowlet.fol.ast.traverse.TraversalAssertions.*;

@SuppressWarnings("DuplicateExpressions")
public class PreOrderTraversalTest {
    @Test
    void shouldApplyRulesToFirstMatch_whenStopRule() {
        given(new Assignment(new VarDecl(new Symbol("a"), new ScalarType(new Symbol("Int"))),
                new Assignment(new VarDecl(new Symbol("b"), new ScalarType(new Symbol("Int"))),
                        new IntLiteral(42))))
                .when(preStop(node -> switch (node) {
                    case Assignment(Expression ignored, Expression rhs) ->
                        new Assignment(new Symbol("a"), rhs);
                    default -> null;
                }))
                .then(new Assignment(new Symbol("a"),
                        new Assignment(new VarDecl(new Symbol("b"), new ScalarType(new Symbol("Int"))),
                                new IntLiteral(42))));
    }

    @Test
    void shouldApplyRulesToEveryMatch_whenContinueRule() {
        given(new Assignment(new VarDecl(new Symbol("a"), new ScalarType(new Symbol("Int"))),
                new Assignment(new VarDecl(new Symbol("b"), new ScalarType(new Symbol("Int"))),
                        new IntLiteral(42))))
                .when(preContinue(node -> switch (node) {
                    case Assignment(Expression ignored, Expression rhs) ->
                            new Assignment(new Symbol("a"), rhs);
                    default -> null;
                }))
                .then(new Assignment(new Symbol("a"),
                        new Assignment(new Symbol("a"), new IntLiteral(42))));
    }

    @Test
    void shouldApplyRulesInPreOrder() {
        Map<String, Integer> counts = new HashMap<>();

        given(new Assignment(new Symbol("a"),
                new Assignment(new Symbol("b"), new Symbol("a"))))
                .when(preContinue(node -> switch (node) {
                    case Symbol(String name) ->
                        new Symbol(name + counts.merge(name, 1, Integer::sum));
                    default -> null;
                }))
                .then(new Assignment(new Symbol("a1"),
                        new Assignment(new Symbol("b1"), new Symbol("a2"))));
    }
}
