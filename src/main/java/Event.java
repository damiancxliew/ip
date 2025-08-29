public class Event extends Task{
    protected String startTime;
    protected String endTime;

    public Event(String description, String startTime, String endTime){
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String encodeString() {
        return String.format("%s | %d | %s | %s | %s", getType(), isDone ? 1 : 0, description, startTime, endTime);
    }
}
