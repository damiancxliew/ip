import java.util.ArrayList;
import java.util.Scanner;

public class DaBot {
    private static ArrayList<String> list = new ArrayList<>();

    private static void addTask(String word) {
        list.add(word);
        System.out.println("added: " + word);
    }

    private static void printTasks() {
        for (int i = 1; i < list.size() + 1; i++) {
            System.out.println(i + ". " + list.get(i - 1));
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello! I'm DaBot\n");
        System.out.println("What can I do for you?\n");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String word = scanner.nextLine();
            if (word.contains("bye")) {
                System.out.println("Bye. Hope to see you again soon!\n");
                break;
            } else if (word.contains("list")) {
                printTasks();
            } else {
                addTask(word);
            }
        }
    }
}
