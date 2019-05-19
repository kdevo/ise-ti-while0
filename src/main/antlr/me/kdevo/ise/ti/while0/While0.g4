grammar While0;

@header {
package me.kdevo.ise.ti.while0;
}
 
program: header? body*;

// -------------
// * HEADER *
// -------------

header: headerSignature DELIM (headerLocals DELIM)*;
headerSignature: headerProgramName LBRACKET (headerIn | headerIn? DELIM headerOut) RBRACKET;
headerIn: KW_IN var (COMMA var)*;
headerOut: KW_OUT var (COMMA var)*;

headerProgramName: VAR;
headerLocals: KW_VAR LBRACKET var (COMMA var)* RBRACKET;
var: VAR;

// -------------
// * BODY *
// -------------

body: stmtAssignment (DELIM stmtAssignment)* | stmtWhile;

stmtAssignment: (assignment0 | assignment1);
assignment0: var ASSIGN ZERO;
assignment1: var ASSIGN var PLUS ONE;

stmtWhile: KW_WHILE var NOT_EQ var KW_DO KW_BEGIN body KW_END;

// -------------
// * TERMINALS *
// -------------

// Simple symbols:
LBRACKET: '(';
RBRACKET: ')';

ASSIGN: '=';
NOT: '!';
NOT_EQ: NOT ASSIGN;
PLUS: '+';
ZERO: '0';
ONE: '1';
DELIM: ';';
SEMICOLON: DELIM;
COMMA: ',';

// Keywords:
KW_IN: 'in';
KW_OUT: 'out';
KW_VAR: 'var';
KW_WHILE: 'while';
KW_DO: 'do';
KW_BEGIN: 'begin';
KW_END: 'end';

// Identifier names:
VAR: [a-zA-Z]+[a-zA-Z0-9]*;

// Skip over:
WS: [ \t\r\n]+ -> skip;
