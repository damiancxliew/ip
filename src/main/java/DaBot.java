import java.util.ArrayList;
import java.util.Scanner;

public class DaBot {
    private static ArrayList<Task> list = new ArrayList<>();
    private static final Ui ui = new Ui();

    private static void addTask(String description) {
        list.add(new Task(description));
        System.out.println("added: " + description);
    }

    private static void printTasks() {
        System.out.println("Here are the tasks in your list:\n");
        for (int i = 1; i < list.size() + 1; i++) {
            System.out.println(i + ". " + list.get(i - 1));
        }
    }

    public static void main(String[] args) {
        ui.showWelcome();

        while (true) {
            String description = ui.readLine();
            if (description.contains("bye")) {
                ui.showBye();
                break;
            } else if (description.contains("list")) {
                printTasks();
            } else if (description.contains("unmark")) {
                String[] words = description.split(" ");
                Task task = list.get(Integer.parseInt(words[1]) - 1);
                task.markAsUndone();
                System.out.println("OK, I've marked this task as not done yet:\n");
                System.out.println(task);
            } else if (description.contains("mark")) {
                String[] words = description.split(" ");
                Task task = list.get(Integer.parseInt(words[1]) - 1);
                task.markAsDone();
                System.out.println("Nice! I've marked this task as done:\n");
                System.out.println(task);
            } else {
                addTask(description);
            }
        }
    }
}
