public class Err {

    public static void error(String msg, int line){
        System.err.println("Error: " + msg + " at line: " + line);
    }

    public static void error(String msg){
        System.err.println("Error: " + msg);
    }
}
