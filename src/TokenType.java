enum TokenType {

    // One char tokens
    L_PAREN, R_PAREN,
    L_BRACE, R_BRACE,
    PLUS, MINUS, DIV, MUL,
    COMMA, DOT, SEMICOLON,

    // One or Two char tokens
    EQUAL, EQUAL_EQUAL,
    NOT, NOT_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,
    DOUBLE_STAR,

    // Literals
    IDENT, NUMBER, STRING,

    // Keywords
    IF, ELSE, WHILE,
    FOR, AND, OR,
    TRUE, FALSE, FN,
    RETURN, THIS, VAR,
    NIL, CLASS, SUPER,

    EOF
}