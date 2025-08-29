import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    protected LocalDate startTime;
    protected LocalDate endTime;

    public Event(String description, String startTime, String endTime){
        super(description);
        this.startTime = LocalDate.parse(startTime);
        this.endTime = LocalDate.parse(endTime);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + startTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + " to: " + endTime.format(DateTimeFormatter.ofPattern(("MMM dd yyyy"))) + ")";
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String encodeString() {
        return String.format("%s | %d | %s | %s | %s", getType(), isDone ? 1 : 0, description,
                startTime.toString(), endTime.toString());
    }
}
