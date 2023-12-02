grammar fol;

@parser::header {
import com.foxowlet.fol.ast.*;
import java.util.function.Function;
}

@parser::members {
private <T, R extends Node> NodeSeq<R> seq(List<T> ctxs, Function<T, R> fn) {
    return new NodeSeq<>(ctxs.stream().map(fn).toList());
}
private <T> Expression reduceUsage(Expression base, List<T> ctxs, Function<T, Function<Expression, Expression>> fn) {
    Expression expr = base;
    for (T ctx : ctxs) {
        expr = fn.apply(ctx).apply(expr);
    }
    return expr;
}
private String num(String input) {
    return input.replaceAll("[_lLbB]", "");
}
}

file returns[File expr]
     : (exprs+=expression)* EOF {$expr = new File(seq($exprs, e -> e.expr)); }
     ;

expression returns[Expression expr]
           : var_decl {$expr = $var_decl.expr;}
           | function_decl {$expr = $function_decl.expr;}
           | struct_decl {$expr = $struct_decl.expr;}
           | lambda_call {$expr = $lambda_call.expr;}
           | usage_chain {$expr = $usage_chain.expr;}
           | if_expression {$expr = $if_expression.expr;}
           | assignment {$expr = $assignment.expr;}
           | block {$expr = $block.expr;}
           | lambda {$expr = $lambda.expr;}
           ;

var_decl returns[VarDecl expr]
         : 'var' symbol type_decl {$expr = new VarDecl($symbol.expr, $type_decl._type);}
         ;
type_decl returns[Type _type]
          : ':' type {$_type = $type._type;}
          ;

if_expression returns[If expr]
   : 'if' '(' condition=expression ')' thenBranch=expression 'else' elseBranch=expression
   {$expr = new If($condition.expr, $thenBranch.expr, $elseBranch.expr); }
   ;

assignment returns[Expression expr]
           : equals {$expr = $equals.expr;}
           | assignment_target '=' expression {$expr = new Assignment($assignment_target.expr, $expression.expr);}
           ;
assignment_target returns[Expression expr]
                  : var_decl {$expr = $var_decl.expr;}
                  // TODO: techinally, function call can't be an assignment target
                  | usage_chain {$expr = $usage_chain.expr;}
                  | symbol {$expr = $symbol.expr;}
                  ;
block returns[Block expr]
      : '{' (exprs+=expression)* '}' {$expr = new Block(seq($exprs, e -> e.expr));}
      ;

lambda returns[Lambda expr]
       : '#(' formals ')' type_decl block {$expr = new Lambda($formals.decls, $type_decl._type, $block.expr);}
       ;
formals returns[NodeSeq<FormalParameter> decls]
        : params+=formal_parameter (',' params+=formal_parameter)* {$decls = seq($params, p -> p.decl); }
        | {$decls = NodeSeq.of();}
        ;
formal_parameter returns[FormalParameter decl]
                 : symbol type_decl {$decl = new FormalParameter($symbol.expr, $type_decl._type);}
                 ;
arguments returns[NodeSeq<Expression> exprs]
          : els+=expression (',' els+=expression)* {$exprs = seq($els, e -> e.expr);}
          | {$exprs = NodeSeq.of();}
          ;
function_decl returns[FunctionDecl expr]
              : 'def' symbol '(' formals ')' type_decl block
              {$expr = new FunctionDecl($symbol.expr, $formals.decls, $type_decl._type, $block.expr);}
              ;

struct_decl returns[StructDecl expr]
            : 'struct' symbol '(' field_decls ')' {$expr = new StructDecl($symbol.expr, $field_decls.decls);}
            ;
field_decls returns[NodeSeq<FieldDecl> decls]
            : fdecls+=field_decl (',' fdecls+=field_decl)* {$decls = seq($fdecls, f -> f.decl);}
            ;
field_decl returns[FieldDecl decl]
           : symbol type_decl {$decl = new FieldDecl($symbol.expr, $type_decl._type);}
           ;

// function_call : (symbol | function_call | lambda) '(' arguments ')';
// field_access : (symbol | function_call ) ('.' symbol)+;
usage_chain returns[Expression expr]
             : usage_target ctxs+=usage+ {$expr = reduceUsage($usage_target.expr, $ctxs, ctx -> ctx.fn);}
             ;
usage_target returns[Expression expr]
             : symbol {$expr = $symbol.expr;}
             | lambda_call {$expr = $lambda_call.expr;}
             ;
usage returns[Function<Expression, Expression> fn]
      : '(' arguments ')' {var exprs = $arguments.exprs; $fn = e -> new FunctionCall(e, exprs);} # function_call
      | '.' symbol {var expr = $symbol.expr; $fn = e -> new FieldAccess(e, expr);} # field_access
      ;

lambda_call returns[FunctionCall expr]
            : lambda '(' arguments ')' {$expr = new FunctionCall($lambda.expr, $arguments.exprs);}
            ;

equals returns[Expression expr]
       : additive_expression {$expr = $additive_expression.expr;}
       | e=equals '==' additive_expression {$expr = new Equals($e.expr, $additive_expression.expr);}
       ;

additive_expression returns[Expression expr]
                    : multiplicative_expression {$expr = $multiplicative_expression.expr;}
                    | e=additive_expression '+' multiplicative_expression {$expr = new Addition($e.expr, $multiplicative_expression.expr);}
                    | e=additive_expression '-' multiplicative_expression {$expr = new Subtraction($e.expr, $multiplicative_expression.expr);}
                    ;

multiplicative_expression returns[Expression expr]
                    : arithmetic_factor {$expr = $arithmetic_factor.expr;}
                    | e=multiplicative_expression '*' arithmetic_factor {$expr = new Multiplication($e.expr, $arithmetic_factor.expr);}
                    | e=multiplicative_expression '/' arithmetic_factor {$expr = new Division($e.expr, $arithmetic_factor.expr);}
                    ;

arithmetic_factor returns[Expression expr]
                  : lambda_call {$expr = $lambda_call.expr;}
                  | usage_chain {$expr = $usage_chain.expr;}
                  | symbol {$expr = $symbol.expr;}
                  | literal {$expr = $literal.expr;}
                  | '(' expression ')' {$expr = $expression.expr;}
                  ;

type returns[Type _type]
     : scalar_type {$_type = $scalar_type._type;}
     | function_type {$_type = $function_type._type;}
     ;
scalar_type returns[Type _type]
            : symbol {$_type = new ScalarType($symbol.expr);}
            ;
function_type returns[Type _type]
              : arg_type '->' type {$_type = new FunctionType($arg_type._type, $type._type); }
              ;
arg_type returns[Type _type]
         : scalar_type {$_type = $scalar_type._type;}
         | '(' type ')' {$_type = $type._type;}
         ;

symbol returns [Symbol expr]
       : IDENTIFIER {$expr = new Symbol($IDENTIFIER.text);}
       ;
literal returns[Literal expr]
        : INT {$expr = new IntLiteral(Integer.parseInt(num($INT.text)));}
        | LONG {$expr = new LongLiteral(Long.parseLong(num($LONG.text)));}
        | BYTE {$expr = new ByteLiteral(Byte.parseByte(num($BYTE.text)));}
        ;

IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]*;
INT : [0-9][0-9_]*;
LONG : [0-9][0-9_]*[lL];
BYTE : [0-9][0-9_]*[bB];

WHITESPACE : [ \t\r\n]+ -> skip;
COMMENT : ('//' | ';') ~[\r\n]* -> channel(HIDDEN);