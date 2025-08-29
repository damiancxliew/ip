public class Todo extends Task{

    public Todo(String description){
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String getType() {
        return "T";
    }

    @Override
    public String encodeString() {
        return String.format("%s | %d | %s", getType(), isDone ? 1 : 0, description);
    }
}
