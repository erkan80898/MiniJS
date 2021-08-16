public class Evaluator {

    void eval(Expression expr){
        try {
            Object val = expr.eval();
            System.out.println(val);
        } catch (RuntimeException e){
            Err.error(e.toString());
        }
    }

}
