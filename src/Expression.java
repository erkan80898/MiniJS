/**
 * Represents the AST
 */
abstract class Expression {

    static private Visitor.Eval evalVisitor = new Visitor.Eval();

    abstract Object eval();

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

        public Object eval(){
            return evalVisitor.eval(this);
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

        @Override
        public String toString() {
            return "Binary{" +
                    "lhs=" + lhs +
                    ", operator=" + op.lexeme +
                    ", rhs=" + rhs +
                    '}';
        }

        public Object eval(){
            return evalVisitor.eval(this);
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

        public Object eval(){
            return evalVisitor.eval(this);
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

        public Object eval(){
            return evalVisitor.eval(this);
        }
    }
}
