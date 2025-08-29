package dabot.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>(100);
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> asList() {
        return tasks;
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public void mark(int index) {
        tasks.get(index).markAsDone();
    }

    public void unmark(int index) {
        tasks.get(index).markAsUndone();
    }

    public List<Task> tasksOn(LocalDate date) {
        List<Task> output = new ArrayList<>();
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.byDate != null && deadline.byDate.equals(date)) {
                    output.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if ((event.startTime != null && event.startTime.equals(date))
                        || (event.endTime != null && event.endTime.equals(date))) {
                    output.add(task);
                }
            }
        }
        return output;
    }
}
