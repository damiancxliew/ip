package dabot.task;

/**
 * Represents a simple todo task.
 * Stores only a description and completion status.
 */
public class Todo extends Task {

    /**
     * Creates a new {@code Todo} with the given description.
     *
     * @param description the description of the todo
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string form of this todo task.
     *
     * @return string with type and status
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the type of this task.
     *
     * @return {@code "T"} for todo
     */
    @Override
    public String getType() {
        return "T";
    }

    /**
     * Encodes this todo for storage.
     *
     * @return encoded string form
     */
    @Override
    public String encodeString() {
        return String.format("%s | %d | %s", getType(), isDone ? 1 : 0, description);
    }
}
