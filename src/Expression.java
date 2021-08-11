/**
 * Represents the AST
 */
abstract class Expression {

    static class Unary extends Expression {
        final Token op;
        final Expression operand;

        @Override
        public String toString() {
            return "Unary{" +
                    "operator=" + op.lexeme +
                    ", operand=" + operand +
                    '}';
        }

        Unary(Token op, Expression operand){
            this.op = op;
            this.operand = operand;
        }

        public Token getOp(){
            return op;
        }

        public Expression getOperand(){
            return operand;
        }
    }

    static class Binary extends Expression {
        final Expression lhs;
        final Token op;
        final Expression rhs;

        Binary(Expression lhs, Token op, Expression rhs){
            this.lhs = lhs;
            this.op = op;
            this.rhs = rhs;
        }

        public Expression getLhs() {
            return lhs;
        }

        public Token getOp(){
            return op;
        }

        public Expression getRhs(){
            return rhs;
        }

        @Override
        public String toString() {
            return "Binary{" +
                    "lhs=" + lhs +
                    ", operator=" + op.lexeme +
                    ", rhs=" + rhs +
                    '}';
        }
    }

    static class Literal<T> extends Expression {
        final T value;
        Literal(T value){
            this.value = value;
        }

        @Override
        public String toString() {
            return "Literal{" +
                    "value=" + value +
                    '}';
        }
    }

    static class Group extends Expression {
        final Expression expression;
        Group(Expression expression){
            this.expression = expression;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "expression=" + expression +
                    '}';
        }
    }
}
