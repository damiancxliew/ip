package dabot.main;

import dabot.task.Deadline;
import dabot.task.Event;
import dabot.task.Task;
import dabot.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Utility class that parses user input strings into actionable commands
 * and {@link Task} objects.
 * <p>
 * Provides methods to interpret text commands such as creating a {@link Todo},
 * {@link Deadline}, or {@link Event}, extracting task indices, and parsing dates.
 * </p>
 */
public class Parser {

    /**
     * Parses a user command string into a specific {@link Task}.
     * <p>
     * Recognized formats are:
     * <ul>
     *   <li>{@code todo DESCRIPTION}</li>
     *   <li>{@code deadline DESCRIPTION /by DATE}</li>
     *   <li>{@code event DESCRIPTION /from START /to END}</li>
     * </ul>
     * </p>
     *
     * @param input the full command string entered by the user
     * @return a corresponding {@link Task} object
     * @throws DabotException if the command is unrecognized or required parts are missing
     */
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

    /**
     * Parses a command string that contains a one-based task index.
     * <p>
     * Used for commands like {@code mark 2}, {@code unmark 5}, or {@code delete 3}.
     * </p>
     *
     * @param input the full command string
     * @return the one-based index parsed from the command
     * @throws DabotException if the index is missing or not a positive integer
     */
    public static int parseIndex1(String input) throws DabotException {
        String[] words = input.trim().split("\\s+");
        assert words.length >= 2 : "parseIndex1 assumes input contains at least two tokens";
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

    /**
     * Parses a command string that specifies a date.
     * <p>
     * Used for commands like {@code on yyyy-MM-dd}.
     * </p>
     *
     * @param input the full command string
     * @return the parsed {@link LocalDate}
     * @throws DabotException if the date is missing or not in {@code yyyy-MM-dd} format
     */
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

    /**
     * Parse an optional integer number of days after the keyword (default 3).
     */
    public static int parseRemindDays(String input) throws DabotException {
        String[] words = input.trim().split("\\s+");
        if (words.length == 1) {
            return 3; // default days
        }
        try {
            int d = Integer.parseInt(words[1]);
            if (d <= 0) {
                throw new NumberFormatException();
            }
            return d;
        } catch (NumberFormatException e) {
            throw new DabotException("Please provide a positive number of days, e.g., 'remind 7'.");
        }
    }

}
