import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MiniJS {

    private static Parser parser;
    private static Scanner scanner;
    private static Expression ast;
    private static List<Token> tokens;
    private static Evaluator evaluator = new Evaluator();

    public static void main(String args[]) throws FileNotFoundException {
        if (args.length > 0){
            runScanner(args[0]);
        }else{
            runScanner();
        }
    }

    public static void runScanner(String filename){
        File file = new File(filename);
        try (java.util.Scanner reader = new java.util.Scanner(file).useDelimiter("\\Z")){
            scanner = new Scanner(reader.next());
            tokens = scanner.scan();
            for (Token token : tokens){
                System.out.println(token);
            }
        }catch (FileNotFoundException e){
            System.err.println("FILE NOT FOUND");
        }

    }
    public static void runScanner(){

        while(true){
            System.out.print("> ");
            java.util.Scanner reader = new java.util.Scanner(System.in);
            String input = reader.nextLine();
            scanner = new Scanner(input);
            tokens = scanner.scan();
            for (Token token : tokens){
                System.out.println(token);
            }
            System.out.println("----PARSING----");
            parser = new Parser(tokens);
            ast = parser.parse();
            System.out.println(ast);
            evaluator.eval(ast);
        }
    }

}
