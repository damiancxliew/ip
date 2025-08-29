import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class DaBot {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public DaBot(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (DabotException e) {
            ui.printError("Loading error: " + e.getMessage());
            loaded = new TaskList();
        }
        tasks = loaded;
    }

    public void run() {
        ui.showWelcome();
        while (true) {
            try {
                String description = ui.readLine();

                if (description.equals("bye")) {
                    ui.showBye();
                    storage.save(tasks.asList());
                    break;

                } else if (description.equals("list")) {
                    ui.showTasks(tasks.asList());

                } else if (description.startsWith("on")) { // Level-8 stretch
                    java.time.LocalDate date = Parser.parseOnDate(description);
                    ui.showTasksOn(date, tasks.tasksOn(date));

                } else if (description.startsWith("mark")) {
                    int idx1 = Parser.parseIndex1(description);
                    tasks.mark(idx1 - 1);
                    storage.save(tasks.asList());
                    ui.showMark(tasks.get(idx1 - 1));

                } else if (description.startsWith("unmark")) {
                    int idx1 = Parser.parseIndex1(description);
                    tasks.unmark(idx1 - 1);
                    storage.save(tasks.asList());
                    ui.showUnmark(tasks.get(idx1 - 1));

                } else if (description.startsWith("delete")) {
                    int idx1 = Parser.parseIndex1(description);
                    Task removed = tasks.delete(idx1 - 1);
                    storage.save(tasks.asList());
                    ui.showDelete(removed, tasks.size());

                } else {
                    Task task = Parser.parseTask(description);
                    tasks.add(task);
                    storage.save(tasks.asList());
                    ui.showAdd(task, tasks.size());
                }
            } catch (DabotException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new DaBot("data/dabot.txt").run();
    }
}

