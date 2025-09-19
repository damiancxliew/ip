package dabot.main;

import java.time.LocalDate;

import dabot.io.Storage;
import dabot.io.Ui;
import dabot.task.Task;
import dabot.task.TaskList;


/**
 * Console (CLI) entry point for DaBot using a small handler-style command router.
 * Mirrors MainApp’s design: a single handle(input) produces a reply string.
 */
public class DaBot {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Constructs a new {@code DaBot} instance with persistent storage.
     * <p>
     * Attempts to load tasks from the specified file. If loading fails,
     * the task list is initialized as empty and an error message will be shown
     * during the first interaction loop.
     *
     * @param filePath the path to the file used for loading and saving tasks
     */
    public DaBot(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (DabotException e) {
            // Start empty if load fails; surface message in first loop iteration
            this.tasks = new TaskList();
        }
    }

    /** Main REPL loop for the CLI. */
    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readLine();
            String reply = handle(input);
            // Print the chatbot reply
            System.out.println(reply);

            if ("bye".equalsIgnoreCase((input == null ? "" : input.trim()).split("\\s+", 2)[0])) {
                break;
            }
        }
    }

    /**
     * Parses a single user input and returns DaBot’s reply.
     * No side-effects beyond mutating tasks + saving when relevant.
     */
    public String handle(String input) {
        String trimmed = input == null ? "" : input.trim();
        if (trimmed.isEmpty()) {
            return "Please enter a command.";
        }
        String cmd = trimmed.split("\\s+", 2)[0];

        try {
            switch (cmd) {
            case "bye":
                return handleBye();
            case "list":
                return handleList();
            case "on":
                return handleOn(trimmed);
            case "mark":
                return handleMark(trimmed);
            case "unmark":
                return handleUnmark(trimmed);
            case "delete":
                return handleDelete(trimmed);
            case "find":
                return handleFind(trimmed);
            case "remind":
                return handleRemind(trimmed);
            default:
                return handleAdd(trimmed);
            }
        } catch (DabotException e) {
            return e.getMessage();
        }
    }

    private String handleBye() throws DabotException {
        save();
        return "Bye. Hope to see you again soon!";
    }

    private String handleList() {
        if (tasks.size() == 0) {
            return "Your list is empty.";
        }
        return "Here are the tasks in your list:\n" + renderTaskList();
    }

    private String handleOn(String input) throws DabotException {
        LocalDate date = Parser.parseOnDate(input);
        var dayTasks = tasks.tasksOn(date);
        if (dayTasks.isEmpty()) {
            return "No tasks on " + date + ".";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks on ").append(date).append(":\n");
        for (Task t : dayTasks) {
            sb.append(t).append('\n');
        }
        return sb.toString().trim();
    }

    private String handleMark(String input) throws DabotException {
        int idx1 = Parser.parseIndex1(input);
        tasks.mark(idx1 - 1);
        save();
        return "Nice! I've marked this task as done:\n" + tasks.get(idx1 - 1);
    }

    private String handleUnmark(String input) throws DabotException {
        int idx1 = Parser.parseIndex1(input);
        tasks.unmark(idx1 - 1);
        save();
        return "OK, I've marked this task as not done yet:\n" + tasks.get(idx1 - 1);
    }

    private String handleDelete(String input) throws DabotException {
        int idx1 = Parser.parseIndex1(input);
        Task removed = tasks.delete(idx1 - 1);
        save();
        return "Noted. I've removed this task:\n" + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleFind(String input) throws DabotException {
        String keyword = Parser.parseFindKeyword(input);
        var found = tasks.find(keyword);
        if (found.isEmpty()) {
            return "No matching tasks for: " + keyword;
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks:\n");
        for (int i = 0; i < found.size(); i++) {
            sb.append(i + 1).append(". ").append(found.get(i)).append('\n');
        }
        return sb.toString().trim();
    }

    private String handleAdd(String input) throws DabotException {
        Task task = Parser.parseTask(input);
        tasks.add(task);
        save();
        return "Got it. I've added this task:\n\t" + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleRemind(String input) throws DabotException {
        int days = Parser.parseRemindDays(input); // e.g. "remind 7"
        var upcoming = tasks.remindersWithinDays(days);
        if (upcoming.isEmpty()) {
            return "No upcoming tasks in the next " + days + " days.";
        }
        StringBuilder sb = new StringBuilder("Here are the upcoming tasks in the next ")
                .append(days).append(" days:\n");
        for (int i = 0; i < upcoming.size(); i++) {
            sb.append(i + 1).append(". ").append(upcoming.get(i)).append('\n');
        }
        return sb.toString().trim();
    }

    private void save() throws DabotException {
        storage.save(tasks.asList());
    }

    private String renderTaskList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append('\n');
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        new DaBot("data/dabot.txt").run();
    }
}
