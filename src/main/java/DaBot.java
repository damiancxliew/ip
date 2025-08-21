import java.util.ArrayList;
import java.util.Scanner;

public class DaBot {
    private static final Storage storage = new Storage();
    private static final Ui ui = new Ui();
    public static void main(String[] args) throws DabotException {
        //Welcome user
        ui.showWelcome();
        while (true) {
            try {
                String description = ui.readLine();
                if (description.contains("bye")) {
                    ui.showBye();
                    break;
                } else if (description.contains("list")) {
                    storage.printTasks();
                } else if (description.startsWith("unmark")) {
                    String[] words = description.split(" ");
                    storage.markTaskAsUndone(words);
                } else if (description.startsWith("mark")) {
                    String[] words = description.split(" ");
                    storage.markTaskAsDone(words);
                } else if (description.startsWith("delete")) {
                    String[] words = description.split(" ");
                    storage.deleteTask(words);
                } else {
                    Task task = Parser.parseTask(description);
                    storage.addTask(task);
                }
            } catch (DabotException e) {
                ui.printError(e.getMessage());
            }
        }
    }
}