package dabot.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dabot.main.DabotException;


/**
 * Represents a list of {@link Task} objects.
 * <p>
 * Provides operations to add, delete, mark, unmark,
 * search by date, and search by keyword.
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
     * Creates a {@code TaskList} with initial tasks.
     *
     * @param initial list of tasks to populate
     */
    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return size of task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return list of tasks
     */
    public ArrayList<Task> asList() {
        return tasks;
    }

    /**
     * Returns the task at a given index.
     *
     * @param index index of the task
     * @return task at that index
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds in TaskList.get()";
        return tasks.get(index);
    }

    /**
     * Adds a task to the list.
     *
     * @param newTasks the tasks to add
     */
    public void add(Task... newTasks) {
        for (Task t : newTasks) {
            tasks.add(t);
        }
    }

    /**
     * Deletes and returns the task at a given index.
     *
     * @param index index of task to delete
     * @return deleted task
     */
    public Task delete(int index) throws DabotException {
        if (index < 0 || index >= tasks.size()) {
            throw new DabotException("Task number " + (index + 1) + " is out of range.");
        }
        return tasks.remove(index);
    }

    /**
     * Marks the task at a given index as done.
     *
     * @param index index of task
     */
    public void mark(int index) throws DabotException {
        if (index < 0 || index >= tasks.size()) {
            throw new DabotException("Task number " + (index + 1) + " is out of range.");
        }
        tasks.get(index).markAsDone();
    }

    /**
     * Marks the task at a given index as not done.
     *
     * @param index index of task
     */
    public void unmark(int index) throws DabotException {
        if (index < 0 || index >= tasks.size()) {
            throw new DabotException("Task number " + (index + 1) + " is out of range.");
        }
        tasks.get(index).markAsUndone();
    }

    /**
     * Returns tasks that occur on the given date.
     *
     * @param date date to filter by
     * @return list of matching tasks
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

    /**
     * Finds all tasks that contain the given keyword.
     *
     * @param keyword keyword to search for
     * @return list of matching tasks
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> output = new java.util.ArrayList<>();
        String keywordLowerCase = keyword.toLowerCase();
        for (Task t : tasks) {
            if (t.toString().toLowerCase().contains(keywordLowerCase)) {
                output.add(t);
            }
        }
        return output;
    }

    /**
     * Returns tasks that are NOT done and due/starting within the next {@code days} (inclusive).
     * Only considers tasks with parseable dates (Deadline.byDate, Event.startTime).
     *
     * @param days number of days from today to check
     * @return list of upcoming undone tasks
     */
    public ArrayList<Task> remindersWithinDays(int days) {
        ArrayList<Task> out = new ArrayList<>();
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate end = today.plusDays(days);

        for (Task t : tasks) {
            if (t.isDone) {
                continue; // only remind for undone tasks
            }
            if (t instanceof Deadline) {
                Deadline d = (Deadline) t;
                if (d.byDate != null && !d.byDate.isBefore(today) && !d.byDate.isAfter(end)) {
                    out.add(t);
                }
            } else if (t instanceof Event) {
                Event e = (Event) t;
                if (e.startTime != null && !e.startTime.isBefore(today) && !e.startTime.isAfter(end)) {
                    out.add(t);
                }
            }
        }
        return out;
    }


}
