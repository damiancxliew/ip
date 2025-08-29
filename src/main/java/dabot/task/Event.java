package dabot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start and end time.
 * <p>
 * The start and end times are stored both as raw strings
 * (to preserve the user’s original input) and as {@link LocalDate}
 * objects when the input can be parsed in {@code yyyy-MM-dd} format.
 * </p>
 */
public class Event extends Task {
    protected String startRaw;
    protected String endRaw;
    protected LocalDate startTime;
    protected LocalDate endTime;

    /**
     * Constructs a new {@code Event} with a description, start time, and end time.
     * <p>
     * If the {@code startRaw} or {@code endRaw} strings are valid ISO dates
     * ({@code yyyy-MM-dd}), they are also stored as {@link LocalDate} objects.
     * Otherwise, the corresponding {@link LocalDate} field is set to {@code null}.
     * </p>
     *
     * @param description description of the event
     * @param startRaw    start time as entered by the user
     * @param endRaw      end time as entered by the user
     */
    public Event(String description, String startRaw, String endRaw) {
        super(description);
        this.startRaw = startRaw;
        this.endRaw = endRaw;
        try {
            this.startTime = LocalDate.parse(startRaw);
        } catch (DateTimeParseException e) {
            this.startTime = null;
        }
        try {
            this.endTime = LocalDate.parse(endRaw);
        } catch (DateTimeParseException e) {
            this.endTime = null;
        }
    }

    /**
     * Returns a string representation of the event task.
     * <p>
     * Dates are formatted as {@code MMM dd yyyy} (e.g., {@code Dec 02 2019})
     * if they were successfully parsed; otherwise, the raw user input is shown.
     * </p>
     *
     * @return the string representation of the event task
     */
    @Override
    public String toString() {
        String startStr = (startTime != null)
                ? startTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                : startRaw;
        String endStr = (endTime != null)
                ? endTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                : endRaw;
        return "[E]" + super.toString() + " (from: " + startStr + " to: " + endStr + ")";
    }

    /**
     * Returns the type of this task as a single-letter code.
     *
     * @return {@code "E"} to denote an event
     */
    @Override
    public String getType() {
        return "E";
    }

    /**
     * Encodes this event into a string suitable for storage.
     * <p>
     * Format: {@code E | doneFlag | description | startRaw | endRaw}
     * where {@code doneFlag} is {@code 1} if done, {@code 0} otherwise.
     * </p>
     *
     * @return the encoded string representation of the event
     */
    @Override
    public String encodeString() {
        return String.format("%s | %d | %s | %s | %s", getType(), isDone ? 1 : 0, description,
                this.startRaw, this.endRaw);
    }
}
