package dabot.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of {@link Task} objects.
 * <p>
 * Provides operations for adding, deleting, retrieving, and updating
 * tasks. Also supports searching for tasks that occur on a specific date.
 * </p>
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>(100);
    }

    /**
     * Creates a {@code TaskList} initialized with a given set of tasks.
     *
     * @param initial the initial list of tasks
     */
    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return the number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the internal list of tasks.
     *
     * @return the list of tasks
     */
    public ArrayList<Task> asList() {
        return tasks;
    }

    /**
     * Returns the task at a given index.
     *
     * @param index zero-based index of the task
     * @return the task at the given index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a new task to the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index zero-based index of the task
     * @return the removed task
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index zero-based index of the task
     */
    public void mark(int index) {
        tasks.get(index).markAsDone();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index zero-based index of the task
     */
    public void unmark(int index) {
        tasks.get(index).markAsUndone();
    }

    /**
     * Returns all tasks that occur on a given date.
     * <p>
     * For {@link Deadline} tasks, the deadline date must match exactly.
     * For {@link Event} tasks, either the start or end date must match.
     * </p>
     *
     * @param date the date to filter tasks by
     * @return a list of tasks occurring on the given date
     */
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
