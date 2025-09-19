package dabot.io;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dabot.task.Task;
/**
 * Handles all user interactions with the chatbot.
 * <p>
 * The {@code Ui} class is responsible for reading user input,
 * displaying messages, and printing feedback about tasks
 * and operations performed by the chatbot.
 * </p>
 */
public class Ui {
    private static final String LONG_LINE = "---------------------------";

    private final Scanner scanner;

    /**
     * Constructs a {@code Ui} object with a {@link Scanner} to read user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a non-empty line of input from the user.
     * Keeps prompting until a non-blank line is entered.
     *
     * @return the trimmed user input line
     */
    public String readLine() {
        String line;
        // keep asking until input is non-empty
        do {
            line = scanner.nextLine().trim();
        } while (line.isEmpty());
        return line;
    }

    /**
     * Displays the welcome message when the chatbot starts.
     */
    public void showWelcome() {
        System.out.println(LONG_LINE);
        System.out.println("Hello! I'm DaBot");
        System.out.println("What can I do for you?");
        System.out.println(LONG_LINE);
    }

    /**
     * Displays the goodbye message when the chatbot exits.
     */
    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints an error message to the user.
     *
     * @param message the error message to display
     */
    public void printError(String message) {
        System.out.println(message);
    }

    /**
     * Shows a confirmation message when a task is added.
     *
     * @param task  the task that was added
     * @param count the new total number of tasks
     */
    public void showAdd(Task task, int count) {
        System.out.println("Got it. I've added this task:\n\t" + task + "\n");
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Shows a message when a task is marked as done.
     *
     * @param task the task that was marked done
     */
    public void showMark(Task task) {
        System.out.println("Nice! I've marked this task as done:\n");
        System.out.println(task);
    }

    /**
     * Shows a message when a task is marked as not done.
     *
     * @param task the task that was unmarked
     */
    public void showUnmark(Task task) {
        System.out.println("OK, I've marked this task as not done yet:\n");
        System.out.println(task);
    }

    /**
     * Shows a message when a task is deleted.
     *
     * @param task  the task that was deleted
     * @param count the new total number of tasks
     */
    public void showDelete(Task task, int count) {
        System.out.println("Noted. I've removed this task:\n\t" + task + "\n");
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Displays all tasks in the list.
     *
     * @param tasks the tasks to display
     */
    public void showTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays all tasks that occur on a specific date.
     *
     * @param date  the date to filter tasks by
     * @param tasks the list of tasks that occur on the date
     */
    public void showTasksOn(LocalDate date, List<Task> tasks) {
        System.out.println("Here are the tasks on " + date + ":");
        for (Task t : tasks) {
            System.out.println(t);
        }
    }

    /**
     * Displays the list of tasks that match a search keyword.
     *
     * @param matches the list of tasks that matched the search
     */
    public void showFind(ArrayList<Task> matches) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + "." + matches.get(i));
        }
    }
}
