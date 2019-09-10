import java.util.Scanner;

public class Prompter {
    
    static Scanner scanner = new Scanner(System.in);
    
    public static String prompt(String prefix) {
        System.out.print(prefix);
        String answer = scanner.nextLine();
        System.out.println();
        if (!answer.equals("y") && !answer.equals("n")) {
        	System.out.println("You must answer with a \"n\" or a \"y\"?");
        	prompt(prefix);
        }
        return answer;
    }
    
}
