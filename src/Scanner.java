import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String input;
    private final static Map<String, TokenType> keywords;
    private List tokens;
    private int slowPointer;
    private int fastPointer;
    private int line;


    static {
        keywords = new HashMap();
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
        keywords.put("for", TokenType.FOR);
        keywords.put("and", TokenType.AND);
        keywords.put("or", TokenType.OR);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("fn", TokenType.FN);
        keywords.put("return", TokenType.RETURN);
        keywords.put("this", TokenType.THIS);
        keywords.put("var", TokenType.VAR);
        keywords.put("nil", TokenType.NIL);
        keywords.put("class", TokenType.CLASS);
        keywords.put("super", TokenType.SUPER);
    }

    Scanner(String input){
        this.input = input;
        tokens = new ArrayList<Token>();
        slowPointer = 0;
        fastPointer = 0;
        line = 1;
    }

    private void advance(){
        fastPointer += 1;
    }

    private char peek(int step){
        if (fastPointer + step >= input.length() || fastPointer < 0) return '\0';

        return input.charAt(fastPointer+step);
    }


    private boolean match(char x){
        return !isEnd() && x == peek(1);
    }

    private void reset(){
        slowPointer = fastPointer;
    }

    private boolean isEnd(){
        return fastPointer >= input.length();
    }

    private boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private boolean isAlphaNum(char c){
        return isAlpha(c) || isDigit(c);
    }

    private void addToken(TokenType type,Object literal){
        advance();
        tokens.add(
                new Token(type,
                        input.substring(slowPointer, fastPointer),
                        literal,
                        line));
        reset();
    }

    private void buildString(){
        while(!isEnd()){
            // Keep scanning, even if its invalid - just report it
            if(match('"')) {
                advance();
                addToken(TokenType.STRING, input.substring(slowPointer, fastPointer + 1));
                break;
            }else if(match('\0')){
                Err.error("Unterminated string", line);
                advance();
            }else{
                advance();
            }
        }
    }

    private void buildDigit(){

        while (isDigit(peek(0))) advance();

        if (match('.')) advance();

        while (isDigit(peek(0))) advance();

        addToken(TokenType.NUMBER,
                Double.parseDouble(input.substring(slowPointer, fastPointer)));

    }

    private void buildIdentOrKey(){
        while (isAlphaNum(peek(1))){
            advance();
        }
        String literal = input.substring(slowPointer, fastPointer + 1);

        if (keywords.containsKey(literal)){
            addToken(keywords.get(literal), null);
        }else{
            addToken(TokenType.IDENT, literal);
        }
    }

    List<Token> scan(){

        while (!isEnd()){
            char c = peek(0);
            switch (c){
                case '(': addToken(TokenType.L_PAREN,null); break;
                case ')': addToken(TokenType.R_PAREN, null); break;
                case '{': addToken(TokenType.L_BRACE, null); break;
                case '}': addToken(TokenType.R_BRACE, null); break;
                case '+': addToken(TokenType.PLUS, null); break;
                case '-': addToken(TokenType.MINUS, null); break;
                case '/': addToken(TokenType.DIV, null); break;
                case ',': addToken(TokenType.COMMA, null); break;
                case '.': addToken(TokenType.DOT, null); break;
                case ';': addToken(TokenType.SEMICOLON, null); break;
                case '*':
                    if (match('*')) {
                        advance();
                        addToken(TokenType.DOUBLE_STAR, null);
                    } else {
                        addToken(TokenType.STAR, null);
                    } break;
                case '=':
                    if (match('=')) {
                        advance();
                        addToken(TokenType.EQUAL_EQUAL, null);
                    } else {
                        addToken(TokenType.EQUAL, null);
                    } break;
                case '!':
                    if (match('=')) {
                        advance();
                        addToken(TokenType.NOT_EQUAL, null);
                    } else {
                        addToken(TokenType.NOT, null);
                    } break;
                case '>':
                    if (match('=')) {
                        advance();
                        addToken(TokenType.GREATER_EQUAL, null);
                    } else {
                        addToken(TokenType.GREATER, null);
                    } break;
                case '<':
                    if (match('=')) {
                        advance();
                        addToken(TokenType.LESS_EQUAL, null);
                    } else {
                        addToken(TokenType.LESS, null);
                    } break;
                case '"':
                    buildString();
                    break;
                case '\n':
                    line++;
                    advance();
                    break;
                case ' ':
                case '\r':
                case '\t':
                    advance();
                    break;
                default:
                    if (isDigit(c)){
                        buildDigit();
                    }else if(isAlpha(c)){
                        buildIdentOrKey();
                    }else{
                        Err.error("Unexpected char " + c, line);
                        advance();
                    }
            }
        }

        tokens.add(new Token(TokenType.EOF, "\0", null, line));

        return tokens;
    }
}