package dabot.task;

import dabot.main.DabotException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsUndone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public abstract String getType();

    public String encodeString() {
        return String.format("%s | %d | %s",
                this.getType(),
                this.isDone ? 1 : 0,
                this.description);
    }

    public static Task decodeString(String line) throws DabotException {
        if (line == null || line.trim().isEmpty()) {
            throw new DabotException("Invalid task line!");
        }

        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new DabotException("Invalid task line: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String desc = parts[2];

        Task task;
        switch (type) {
        case "T":
            task = new Todo(desc);
            break;
        case "D":
            if (parts.length < 4) {
                throw new DabotException("Deadline missing /by parameter: " + line);
            }
            task = new Deadline(desc, parts[3]);
            break;
        case "E":
            if (parts.length < 5) {
                throw new DabotException("Event missing /from or /to: " + line);
            }
            task = new Event(desc, parts[3], parts[4]);
            break;
        default:
            throw new DabotException("Unknown task type: " + type);
        }
        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
