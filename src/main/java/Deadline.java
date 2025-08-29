import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    protected LocalDate by;

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
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
                this.by.toString());
    }
}
