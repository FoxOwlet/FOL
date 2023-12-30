package com.foxowlet.fol.parser;

import com.foxowlet.fol.ast.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AntlrParserTest {
    private final AntlrParser parser = new AntlrParser();

    @Test
    void shouldParseSymbol() {
        Expression actual = parse("a");

        File expected = makeFile(new Symbol("a"));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseFunctionType() {
        Expression actual = parse("var a: Int->Long->Unit");

        File expected = makeFile(
                new VarDecl(new Symbol("a"),
                        new FunctionType(
                                new ScalarType(new Symbol("Int")),
                                new FunctionType(new ScalarType(new Symbol("Long")),
                                        new ScalarType(new Symbol("Unit"))))));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseFunctionType_whenArgumentIsFunction() {
        Expression actual = parse("var a: (Int->Long)->Unit");

        File expected = makeFile(
                new VarDecl(new Symbol("a"),
                        new FunctionType(
                                new FunctionType(new ScalarType(new Symbol("Int")),
                                        new ScalarType(new Symbol("Long"))),
                                new ScalarType(new Symbol("Unit")))));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseArithmeticExpressionWithCorrectOperatorPriority() {
        Expression actual = parse("1 + 2 * 3 + 4");

        File expected = makeFile(
                new Addition(
                        new Addition(
                                new IntLiteral(1),
                                new Multiplication(
                                        new IntLiteral(2),
                                        new IntLiteral(3))),
                        new IntLiteral(4)));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseArithmeticExpressionWithCorrectParensPriority() {
        Expression actual = parse("1 + 2 * (3 + 4)");

        File expected = makeFile(
                new Addition(
                        new IntLiteral(1),
                        new Multiplication(
                                new IntLiteral(2),
                                new Addition(
                                        new IntLiteral(3),
                                        new IntLiteral(4)))));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseChainedFieldAccess() {
        Expression actual = parse("a.b.c");

        File expected = makeFile(
                new FieldAccess(
                        new FieldAccess(
                                new Symbol("a"),
                                new Symbol("b")
                        ),
                        new Symbol("c")));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseChainedFunctionCalls() {
        Expression actual = parse("a()(1)(2,3)");

        File expected = makeFile(
                new FunctionCall(
                        new FunctionCall(
                                new FunctionCall(
                                        new Symbol("a"),
                                        NodeSeq.of()),
                                NodeSeq.of(new IntLiteral(1))),
                        NodeSeq.of(new IntLiteral(2), new IntLiteral(3))));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseMixedUsage() {
        Expression actual = parse("a.b().c");

        File expected = makeFile(
                new FieldAccess(
                        new FunctionCall(
                                new FieldAccess(
                                        new Symbol("a"),
                                        new Symbol("b")),
                                NodeSeq.of()),
                        new Symbol("c")));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseLambdaCall() {
        Expression actual = parse("#(): Unit {}()");

        File expected = makeFile(
                new FunctionCall(
                        new Lambda(
                                NodeSeq.of(),
                                new ScalarType(new Symbol("Unit")),
                                new Block(NodeSeq.of())),
                        NodeSeq.of()));

        assertEquals(expected, actual);
    }

    @Test
    void shouldSupportLambdaCall_whenIsChainedFunctionCallsTarget() {
        Expression actual = parse("#(): Unit {}()()");

        File expected = makeFile(
                new FunctionCall(
                        new FunctionCall(
                                new Lambda(
                                        NodeSeq.of(),
                                        new ScalarType(new Symbol("Unit")),
                                        new Block(NodeSeq.of())),
                                NodeSeq.of()),
                        NodeSeq.of()));

        assertEquals(expected, actual);
    }

    @Test
    void shouldSupportLambdaCall_whenIsFieldAccessTarget() {
        Expression actual = parse("#(): Unit {}().a");

        File expected = makeFile(
                new FieldAccess(
                        new FunctionCall(
                                new Lambda(
                                        NodeSeq.of(),
                                        new ScalarType(new Symbol("Unit")),
                                        new Block(NodeSeq.of())),
                                NodeSeq.of()),
                        new Symbol("a")));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseFunctionDecl() {
        Expression actual = parse("def foo(a: Int, b: Long): Unit {}");

        File expected = makeFile(
                new FunctionDecl(
                        new Symbol("foo"),
                        NodeSeq.of(
                                new FormalParameter(new Symbol("a"), new ScalarType(new Symbol("Int"))),
                                new FormalParameter(new Symbol("b"), new ScalarType(new Symbol("Long")))),
                        new ScalarType(new Symbol("Unit")),
                        new Block(NodeSeq.of())));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseStructDecl() {
        Expression actual = parse("struct Foo(a: Int, b: Long)");

        File expected = makeFile(
                new StructDecl(
                        new Symbol("Foo"),
                        NodeSeq.of(
                                new FieldDecl(new Symbol("a"), new ScalarType(new Symbol("Int"))),
                                new FieldDecl(new Symbol("b"), new ScalarType(new Symbol("Long"))))));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseFunctionCallSum() {
        Expression actual = parse("foo() + bar()");

        File expected = makeFile(
                new Addition(
                        new FunctionCall(new Symbol("foo"), NodeSeq.of()),
                        new FunctionCall(new Symbol("bar"), NodeSeq.of())));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseIfChain() {
        Expression actual = parse("if (a) 1 else if (b) {2} else {3 4}");

        File expected = makeFile(
                new If(
                        new Symbol("a"),
                        new IntLiteral(1),
                        new If(
                                new Symbol("b"),
                                new Block(NodeSeq.of(new IntLiteral(2))),
                                new Block(NodeSeq.of(new IntLiteral(3), new IntLiteral(4))))));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseEqualsChain_withLeftAssociativity() {
        Expression actual = parse("a == b == c");

        File expected = makeFile(
                new Equals(
                        new Equals(new Symbol("a"), new Symbol("b")),
                        new Symbol("c")));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseSubtractionChain_withLeftAssociativity() {
        Expression actual = parse("1 - 2 - 3");

        File expected = makeFile(
                new Subtraction(
                        new Subtraction(new IntLiteral(1), new IntLiteral(2)),
                        new IntLiteral(3)));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseStringLiteral() {
        Expression actual = parse("\"abc\"");

        File expected = makeFile(new StringLiteral("abc"));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseComplexExpression() {
        Expression actual = parse(
                """
                       a = b = 1 + 2 * (if (c) 3 / 4 / 5 else #(): Int {42}() + 6) + bar.buz()
                       """);

        File expected = makeFile(
                new Assignment(
                        new Symbol("a"),
                        new Assignment(
                                new Symbol("b"),
                                new Addition(
                                        new Addition(
                                                new IntLiteral(1),
                                                new Multiplication(
                                                        new IntLiteral(2),
                                                        new If(
                                                                new Symbol("c"),
                                                                new Division(
                                                                        new Division(
                                                                                new IntLiteral(3),
                                                                                new IntLiteral(4)),
                                                                        new IntLiteral(5)),
                                                                new Addition(
                                                                        new FunctionCall(
                                                                                new Lambda(
                                                                                        NodeSeq.of(),
                                                                                        new ScalarType(new Symbol("Int")),
                                                                                        new Block(NodeSeq.of(new IntLiteral(42)))),
                                                                                NodeSeq.of()),
                                                                        new IntLiteral(6))))),
                                        new FunctionCall(
                                                new FieldAccess(new Symbol("bar"), new Symbol("buz")),
                                                NodeSeq.of())))));

        assertEquals(expected, actual);
    }

    private Expression parse(String input) {
        try {
            return parser.parse(input).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static File makeFile(Expression... content) {
        return new File(NodeSeq.of(content));
    }
}