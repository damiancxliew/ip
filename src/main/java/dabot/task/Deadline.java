package dabot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task that must be completed by a specific date.
 * <p>
 * The date is stored both as the raw string entered by the user
 * and, if possible, as a parsed {@link LocalDate} object in
 * {@code yyyy-MM-dd} format.
 * </p>
 */
public class Deadline extends Task {
    protected String byRaw;
    protected LocalDate byDate;

    /**
     * Constructs a {@code Deadline} with a description and a deadline string.
     * <p>
     * If the {@code by} string can be parsed in ISO format ({@code yyyy-MM-dd}),
     * it is stored as a {@link LocalDate}. Otherwise, {@code byDate} is set to {@code null}
     * and only the raw string is preserved.
     * </p>
     *
     * @param description description of the deadline task
     * @param by          deadline as entered by the user
     */
    public Deadline(String description, String by) {
        super(description);
        this.byRaw = by;
        try {
            this.byDate = LocalDate.parse(by); // if valid, store as date
        } catch (DateTimeParseException e) {
            this.byDate = null;
        }
    }

    /**
     * Returns a string representation of the deadline task.
     * <p>
     * If {@code byDate} is valid, the deadline is formatted as
     * {@code MMM dd yyyy} (e.g., {@code Dec 02 2019});
     * otherwise, the raw string entered by the user is shown.
     * </p>
     *
     * @return the string representation of the deadline task
     */
    @Override
    public String toString() {
        if (byDate != null) {
            return "[D]" + super.toString() + " (by: "
                    + byDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
        } else {
            return "[D]" + super.toString() + "(by: " + byRaw + ")";
        }
    }

    /**
     * Returns the type of this task as a single-letter code.
     *
     * @return {@code "D"} to denote a deadline
     */
    @Override
    public String getType() {
        return "D";
    }

    /**
     * Encodes this deadline task into a string suitable for storage.
     * <p>
     * Format: {@code D | doneFlag | description | byRaw}
     * where {@code doneFlag} is {@code 1} if done, {@code 0} otherwise.
     * </p>
     *
     * @return the encoded string representation of the deadline
     */
    @Override
    public String encodeString() {
        return String.format("%s | %d | %s | %s",
                getType(),
                isDone ? 1 : 0,
                description,
                this.byRaw);
    }
}
