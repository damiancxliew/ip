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

import java.nio.file.Paths;

/**
 * The main JavaFX application class for DaBot.
 * <p>
 * Initializes the task storage, loads tasks from file, and starts the GUI.
 * Provides the {@code handle} method to process user input and return responses.
 */
public class MainApp extends Application {
    private TaskList tasks;
    private Storage storage;

    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for this application.
     * @throws Exception if FXML loading fails.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // init core
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
        try {
            if (input.equals("bye")) {
                storage.save(tasks.asList());
                return "Bye. Hope to see you again soon!";
            } else if (input.equals("list")) {
                StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append(i + 1).append(". ").append(tasks.get(i)).append('\n');
                }
                return sb.toString().trim();
            } else if (input.startsWith("on")) {
                java.time.LocalDate date = Parser.parseOnDate(input);
                var dayTasks = tasks.tasksOn(date);
                if (dayTasks.isEmpty()) return "No tasks on " + date + ".";
                StringBuilder sb = new StringBuilder("Here are the tasks on ").append(date).append(":\n");
                for (Task t : dayTasks) sb.append(t).append('\n');
                return sb.toString().trim();
            } else if (input.startsWith("mark")) {
                int idx1 = Parser.parseIndex1(input);
                tasks.mark(idx1 - 1);
                storage.save(tasks.asList());
                return "Nice! I've marked this task as done:\n" + tasks.get(idx1 - 1);
            } else if (input.startsWith("unmark")) {
                int idx1 = Parser.parseIndex1(input);
                tasks.unmark(idx1 - 1);
                storage.save(tasks.asList());
                return "OK, I've marked this task as not done yet:\n" + tasks.get(idx1 - 1);
            } else if (input.startsWith("delete")) {
                int idx1 = Parser.parseIndex1(input);
                Task removed = tasks.delete(idx1 - 1);
                storage.save(tasks.asList());
                return "Noted. I've removed this task:\n" + removed + "\nNow you have " + tasks.size() + " tasks in the list.";
            } else {
                Task task = Parser.parseTask(input);
                tasks.add(task);
                storage.save(tasks.asList());
                return "Got it. I've added this task:\n\t" + task + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
        } catch (DabotException e) {
            return e.getMessage();
        }
    }
}
