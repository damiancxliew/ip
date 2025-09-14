package dabot.gui;

import dabot.io.Storage;
import dabot.main.DabotException;
import dabot.main.Parser;
import dabot.task.Task;
import dabot.task.TaskList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main JavaFX application class for DaBot.
 * <p>
 * Initializes the task storage, loads tasks from file, and starts the GUI.
 * Provides the {@code handle} method to process user input and return responses.
 */
public class MainApp extends Application {
    private TaskList tasks;
    private Storage storage;

    @Override
    public void start(Stage stage) throws Exception {
        storage = new Storage("data/dabot.txt");
        try {
            tasks = new TaskList(storage.load());
        } catch (DabotException e) {
            tasks = new TaskList();
        }

        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Scene scene = new Scene(fxml.load());
        MainWindow controller = fxml.getController();
        controller.init(this);

        stage.setTitle("DaBot");
        stage.setScene(scene);
        stage.show();

        controller.showWelcome();
    }

    /**
     * Handles a single line of user input and returns DaBot’s reply.
     *
     * @param input The user input command.
     * @return The chatbot’s response string.
     */
    public String handle(String input) {
        String trimmed = input == null ? "" : input.trim();
        if (trimmed.isEmpty()) {
            return "Please enter a command.";
        }

        // first token is the command word
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
        java.time.LocalDate date = Parser.parseOnDate(input);
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

    private String handleAdd(String input) throws DabotException {
        Task task = Parser.parseTask(input);
        tasks.add(task);
        save();
        return "Got it. I've added this task:\n\t" + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
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

    private String handleRemind(String input) throws DabotException {
        int days = Parser.parseRemindDays(input); // e.g., "remind 7" -> 7
        var upcoming = tasks.remindersWithinDays(days);

        if (upcoming.isEmpty()) {
            return "No upcoming tasks in the next " + days + " days.";
        }

        StringBuilder sb = new StringBuilder("Here are the upcoming tasks in the next ")
                .append(days).append(" days:").append('\n');
        for (int i = 0; i < upcoming.size(); i++) {
            sb.append(i + 1).append(". ").append(upcoming.get(i)).append('\n');
        }
        return sb.toString().trim();
    }

}
