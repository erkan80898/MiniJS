import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MiniJS {

    public static void main(String args[]) throws FileNotFoundException {
        Scanner scanner;
        List<Token> tokens;

        if (args.length > 0){
            runScanner(args[0]);
        }else{
            runScanner();
        }
    }

    public static void runScanner(String filename){
        Scanner scanner;
        List<Token> tokens;
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
        Scanner scanner;
        List<Token> tokens;

        while(true){
            System.out.print("> ");
            java.util.Scanner reader = new java.util.Scanner(System.in);
            String input = reader.nextLine();
            scanner = new Scanner(input);
            tokens = scanner.scan();
            for (Token token : tokens){
                System.out.println(token);
            }
        }
    }

}
