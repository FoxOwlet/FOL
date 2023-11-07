grammar fol;

file : expression* EOF;

expression : var_decl
           | function_decl
           | struct_decl
           //| function_call
           //| field_access
           | lambda_call
           | symbol_usage
           | symbol
           | literal
           | assignment
           | arithmetic_expression
           | block
           | lambda;

var_decl : 'var' symbol type_decl;
type_decl : ':' type;
assignment : (var_decl | symbol_usage | symbol) '=' expression;
block : '{' expression* '}';

lambda : '#(' formals ')' type_decl block;
formals : formal_parameter (',' formal_parameter)* | ;
formal_parameter : symbol type_decl;
arguments : expression (',' expression)* | ;
function_decl : 'def' symbol '(' formals ')' type_decl block;

struct_decl : 'struct' symbol '(' field_decls ')';
field_decls : field_decl (',' field_decl)*;
field_decl : symbol type_decl;

//function_call : (symbol | field_access | function_call | lambda) '(' arguments ')';
//field_access : (symbol | function_call | field_access) ('.' symbol)+;
// TODO: this should be a proper tree instead of just a sequence
symbol_usage : symbol usage+;
usage : '(' arguments ')' # function_call
      | '.' symbol # field_access;

// TODO: this should be a part of the function call definition
lambda_call : lambda '(' arguments ')';

arithmetic_expression : addition
                      | arithmetic_term;
arithmetic_term : multiplication
                | arithmetic_factor;
arithmetic_factor : lambda_call | symbol_usage | symbol | literal | '(' expression ')';

addition : arithmetic_term '+' expression;
multiplication : arithmetic_factor '*' expression;

type : scalar_type | function_type;
scalar_type : symbol;
function_type : arg_type '->' type;
arg_type : scalar_type | '(' type ')';

symbol : IDENTIFIER;
literal : INT | LONG | BYTE;

IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]*;
INT : [0-9][0-9_]*;
LONG : [0-9][0-9_]*[lL];
BYTE : [0-9][0-9_]*[bB];

WHITESPACE : [ \t\r\n]+ -> skip;
COMMENT : ('//' | ';') ~[\r\n]* -> skip;