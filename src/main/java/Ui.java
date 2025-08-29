import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String LONG_LINE = "---------------------------";

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
        System.out.println(LONG_LINE);
        System.out.println("Hello! I'm DaBot");
        System.out.println("What can I do for you?");
        System.out.println(LONG_LINE);
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void showAdd(Task task, int count) {
        System.out.println("Got it. I've added this task:\n\t" + task + "\n");
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    public void showMark(Task task) {
        System.out.println("Nice! I've marked this task as done:\n");
        System.out.println(task);
    }

    public void showUnmark(Task task) {
        System.out.println("OK, I've marked this task as not done yet:\n");
        System.out.println(task);
    }

    public void showDelete(Task task, int count) {
        System.out.println("Noted. I've removed this task:\n\t" + task + "\n");
        System.out.println("Now you have " + count + " tasks in the list.");
    }


    public void showTasks(List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showTasksOn(LocalDate date, List<Task> tasks) {
        System.out.println("Here are the tasks on " + date + ":");
        for (Task t : tasks) {
            System.out.println(t);
        }
    }

}
