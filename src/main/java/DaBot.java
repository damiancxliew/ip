import java.util.Scanner;

public class DaBot {
    public static boolean echo(String word) {
        if (word.contains("bye")) {
            System.out.println("Bye. Hope to see you again soon!\n");
            return false;
        }
        System.out.println(word);
        return true;
    }
    public static void main(String[] args) {
        System.out.println("Hello! I'm DaBot\n");
        System.out.println("What can I do for you?\n");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String word = scanner.next();
            if (!echo(word)) {
                break;
            }
        }
    }
}
