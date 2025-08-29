package dabot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task{
    protected String byRaw;
    protected LocalDate byDate;


    public Deadline(String description, String byRaw) {
        super(description);
        this.byRaw = byRaw;
        try {
            this.byDate = LocalDate.parse(byRaw); //if can change to date
        } catch (DateTimeParseException e) {
            this.byDate = null;
        }

    }

    @Override
    public String toString() {
        if (byDate != null) {
            return "[D]" + super.toString() + " (by: "
                    + byDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
        } else {
            return "[D]" + super.toString() + "(by: " + byRaw + ")";
        }
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String encodeString() {
        return String.format("%s | %d | %s | %s",
                getType(),
                isDone ? 1 : 0,
                description,
                this.byRaw);
    }
}
