grammar grammarTesting;

expression: equality;
equality: comparsion (('!=' | '==') comparsion)*;
comparsion: addsub (('<' | '<=' | '>' | '>=') addsub)*;
addsub: muldiv (('+' | '-') muldiv)*;
muldiv: unary (('*' | '/') muldiv)*;
unary: ('!' | '-') unary | base;
base: NUM | STRING | IDENT | BOOL | 'nil' | '(' expression ')' ;

STRING: '"' .* '"';
NUM: DIGIT+ ('.' DIGIT+)?;
BOOL: 'true' | 'false';
IDENT: CHAR+ (CHAR | DIGIT | '_')*;
CHAR: 'a'..'z' | 'A'..'Z';
DIGIT: '0'..'9';


