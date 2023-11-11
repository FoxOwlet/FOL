package com.foxowlet.fol.parser;

import com.foxowlet.fol.ast.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AntlrParserTest {
    private final AntlrParser parser = new AntlrParser();

    @Test
    void shouldParseSymbol() {
        Expression actual = parser.parse("a");

        File expected = makeFile(new Symbol("a"));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseFunctionType() {
        Expression actual = parser.parse("var a: Int->Long->Unit");

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
        Expression actual = parser.parse("var a: (Int->Long)->Unit");

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
        Expression actual = parser.parse("1 + 2 * 3 + 4");

        File expected = makeFile(
                new Addition(
                        new IntLiteral(1),
                        new Addition(
                                new Multiplication(
                                        new IntLiteral(2),
                                        new IntLiteral(3)),
                                new IntLiteral(4))));

        assertEquals(expected, actual);
    }

    @Test
    void shouldParseArithmeticExpressionWithCorrectParensPriority() {
        Expression actual = parser.parse("1 + 2 * (3 + 4)");

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
        Expression actual = parser.parse("a.b.c");

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
        Expression actual = parser.parse("a()(1)(2,3)");

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
        Expression actual = parser.parse("a.b().c");

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
        Expression actual = parser.parse("#(): Unit {}()");

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
        Expression actual = parser.parse("#(): Unit {}()()");

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
        Expression actual = parser.parse("#(): Unit {}().a");

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
        Expression actual = parser.parse("def foo(a: Int, b: Long): Unit {}");

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
        Expression actual = parser.parse("struct Foo(a: Int, b: Long)");

        File expected = makeFile(
                new StructDecl(
                        new Symbol("Foo"),
                        NodeSeq.of(
                                new FieldDecl(new Symbol("a"), new ScalarType(new Symbol("Int"))),
                                new FieldDecl(new Symbol("b"), new ScalarType(new Symbol("Long"))))));

        assertEquals(expected, actual);
    }

    private static File makeFile(Expression... content) {
        return new File(NodeSeq.of(content));
    }
}