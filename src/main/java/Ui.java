import java.util.Scanner;

public class Ui {

    private static final String LONGLINE = "---------------------------";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readLine() {
        String line;
        // keep asking until input is non-empty
        do {
            line = scanner.nextLine().trim();
        } while (line.isEmpty());
        return line;
    }

    public void showWelcome() {
        System.out.println(LONGLINE);
        System.out.println("Hello! I'm DaBot");
        System.out.println("What can I do for you?");
        System.out.println(LONGLINE);
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void printError(String message) {
        System.out.println(message);
    }


}
