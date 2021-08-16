/**
 * Operations that deal on expression/expression statements will live in here
 */
abstract class Visitor{

    static class Eval extends Visitor {
        public Object eval(Expression.Literal literal) {
            return literal.value;
        }

        public Object eval(Expression.Unary unaryExpr) {

            Object rhs = unaryExpr.operand.eval();

            switch (unaryExpr.op.type){
                case MINUS:
                    return -(double)rhs;
                case NOT:
                    return !isTrue(rhs);
            }
            return null;
        }

        public Object eval(Expression.Binary binaryExpr){
            Object lhs = binaryExpr.lhs.eval();
            Object rhs = binaryExpr.rhs.eval();

            switch (binaryExpr.op.type){
                case PLUS:
                    if (lhs instanceof String && rhs instanceof String){
                        String str = lhs + (String)rhs;
                        return (String)lhs + (String)rhs;
                    }else if (lhs instanceof Double && rhs instanceof Double){
                        return (double)lhs + (double)rhs;
                    }else{
                        Err.error("+ OP USE INVALID");
                        return null;
                    }
                case MINUS:
                    return (double)lhs - (double)rhs;
                case MUL:
                    return (double)lhs * (double)rhs;
                case DIV:
                    return (double)lhs / (double)rhs;
                case DOUBLE_STAR:
                    return Math.pow((double)lhs, (double)rhs);
                case LESS:
                    return (double)lhs < (double)rhs;
                case LESS_EQUAL:
                    return (double)lhs <= (double)rhs;
                case GREATER:
                    return (double)lhs > (double)rhs;
                case GREATER_EQUAL:
                    return (double)lhs >= (double)rhs;
                case EQUAL:
                    return isEqual(lhs, rhs);
                case NOT_EQUAL:
                    return !isEqual(lhs, rhs);
            }
            return null;
        }

        public Object eval(Expression.Group groupExpr){
            return groupExpr.expression.eval();
        }
    }


    static boolean isTrue(Object obj){
        if (obj == null) return false;
        if (obj instanceof Boolean) return (boolean) obj;
        return true;
    }

    static boolean isEqual(Object obj1, Object obj2){
        if (obj1 == null && obj2 == null) return true;
        if (obj1 == null) return false;
        return obj1.equals(obj2);
    }
}
