import java.util.ArrayList;
import java.util.Scanner;

public class DaBot {
    private static final Storage storage = new Storage();
    private static final Ui ui = new Ui();

    public static void main(String[] args) {
        ui.showWelcome();

        while (true) {
            String description = ui.readLine();
            if (description.contains("bye")) {
                ui.showBye();
                break;
            } else if (description.contains("list")) {
                storage.printTasks();
            } else if (description.startsWith("unmark")) {
                String[] words = description.split(" ");
                storage.markTaskAsUndone(Integer.parseInt(words[1]) - 1);
            } else if (description.startsWith("mark")) {
                String[] words = description.split(" ");
                storage.markTaskAsDone(Integer.parseInt(words[1]) - 1);
            } else {
                Task task = Parser.parseTask(description);
                storage.addTask(task);
            }
        }
    }
}
