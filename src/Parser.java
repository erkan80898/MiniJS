import java.util.List;

public class Parser {

    List<Token> tokens;
    int current;

    Parser(List<Token> tokens){
        this.tokens = tokens;
        current = -1;
    }

    Expression parse(){
        return expression();
    }

    private void advance(){
        current += 1;
    }

    private Token peek(int step){

        if (current + step >= tokens.size()){
            return null;
        }

        return tokens.get(current + step);
    }

    private boolean match(TokenType... types){
        Token tok = peek(1);
        if (tok == null) return false;

        TokenType currentType = tok.type;

        for(TokenType type: types){
            if (currentType == type){
                return true;
            }
        }
        return false;
    }

    private Expression expression(){
        return equality();
    }

    private Expression equality(){
        //Comparsion cant advance at its return
        Expression lhs = comparsion();

        while (match(TokenType.NOT_EQUAL, TokenType.EQUAL)){
            advance();
            Token op = tokens.get(current);
            Expression rhs = comparsion();
            lhs = new Expression.Binary(lhs, op, rhs);
        }
        return lhs;
    }

    private Expression comparsion(){
        Expression lhs = addsub();

        while (match(TokenType.LESS, TokenType.LESS_EQUAL,
                TokenType.GREATER, TokenType.GREATER_EQUAL)){
            advance();
            Token op = tokens.get(current);
            Expression rhs = addsub();
            lhs = new Expression.Binary(lhs, op, rhs);
        }
        return lhs;
    }

    private Expression addsub(){
        Expression lhs = muldiv();

        while(match(TokenType.PLUS, TokenType.MINUS)){
            advance();
            Token op = tokens.get(current);
            Expression rhs = muldiv();
            lhs = new Expression.Binary(lhs, op, rhs);
        }
        return lhs;
    }

    private Expression muldiv(){
        Expression lhs = square();

        while(match(TokenType.MUL, TokenType.DIV)){
            advance();
            Token op = tokens.get(current);
            Expression rhs = muldiv();
            lhs = new Expression.Binary(lhs, op, rhs);
        }
        return lhs;
    }

    private Expression square(){
        Expression lhs = unary();

        while(match(TokenType.DOUBLE_STAR)){
            advance();
            Token op = tokens.get(current);
            Expression rhs = square();
            lhs = new Expression.Binary(lhs, op, rhs);
        }
        return lhs;
    }

    private Expression unary(){
        Expression rhs;

        if(match(TokenType.NOT, TokenType.MINUS)){
            advance();
            Token op = tokens.get(current);
            rhs = new Expression.Unary(op, unary());
        }else{
            rhs = base();
        }

        return rhs;
    }

    private Expression base() {

        Token tok = peek(1);
        advance();

        if (tok == null) {
            //REPORT ERR
            Err.error("Parse Error: Parsing ended without a finished production");
        }

        switch (tok.type) {
            case NUMBER:
            case STRING:
            case IDENT:
                return new Expression.Literal(tok.literal);
            case L_PAREN:
                Expression exp = new Expression.Group(expression());
                if (match(TokenType.R_PAREN)) {
                    advance();
                    return exp;
                } else {
                    advance();
                    Err.error("Missing right parentheses for grouping - skipping");
                    return null;
                }
        }
        return null;
    }
}
