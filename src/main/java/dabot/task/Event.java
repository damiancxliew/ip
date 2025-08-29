package dabot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task{
    protected String startRaw;
    protected String endRaw;
    protected LocalDate startTime;
    protected LocalDate endTime;

    public Event(String description, String startRaw, String endRaw){
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

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String encodeString() {
        return String.format("%s | %d | %s | %s | %s", getType(), isDone ? 1 : 0, description,
                this.startRaw, this.endRaw);
    }
}
