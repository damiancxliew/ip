package dabot.main;

import dabot.task.Deadline;
import dabot.task.Event;
import dabot.task.Task;
import dabot.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static Task parseTask(String input) throws DabotException {
        String[] words = input.split(" ", 2);
        String taskType = words[0];

        switch (taskType) { // Switch between different task types
            case "todo":
                if (words.length < 2 || words[1].isEmpty()) {
                    throw new DabotException("The description of a todo cannot be empty.");
                }
                return new Todo(words[1].trim());

            case "deadline":
                String[] parts = words[1].split("/by");
                if (parts.length < 2) {
                    throw new DabotException("Deadline must have a /by clause. Add a date!");
                }
                return new Deadline(parts[0].trim(), parts[1].trim());

            case "event":
                String[] timeParts = words[1].split("/from");
                if (timeParts.length < 2) {
                    throw new DabotException("Event must have a /from and /to clause. Add a proper start and end time!");
                }
                String description = timeParts[0].trim();
                String[] fromTo = timeParts[1].split("/to", 2);
                if (fromTo.length < 2) {
                    throw new DabotException("Event must have a /to clause. Add a proper end time!");
                }
                return new Event(description, fromTo[0].trim(), fromTo[1].trim());

            default:
                throw new DabotException("Grrrr, I am sorry, I don't know what that means...");
        }
    }

    public static int parseIndex1(String input) throws DabotException {
        String[] words = input.trim().split("\\s+");
        if (words.length < 2) {
            throw new DabotException("Please specify a task number.");
        }
        try {
            int idx1 = Integer.parseInt(words[1]);
            if (idx1 <= 0) throw new NumberFormatException();
            return idx1;
        } catch (NumberFormatException e) {
            throw new DabotException("Task number must be a positive integer.");
        }
    }

    public static LocalDate parseOnDate(String input) throws DabotException {
        String[] words = input.trim().split("\\s+");
        if (words.length < 2) {
            throw new DabotException("Date (yyyy-MM-dd) cannot be empty.");
        }
        try {
            return LocalDate.parse(words[1]);
        } catch (DateTimeParseException e) {
            throw new DabotException("Please type your date in format yyyy-MM-dd.");
        }
    }

    public static String parseFindKeyword(String input) throws DabotException {
        String[] words = input.trim().split("\\s+", 2);
        if (words.length < 2 || words[1].isEmpty()) {
            throw new DabotException("Find keyword cannot be empty.");
        }
        return words[1];
    }

}
