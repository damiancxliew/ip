import java.util.ArrayList;

public class Storage {
    public static ArrayList<Task> taskList;

    public Storage(){
        taskList = new ArrayList<>(100);
    }

    public void addTask(Task task) {
        taskList.add(task);
        System.out.println("Got it. I've added this task:\n\t" + task + "\n");
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
    }

    public void printTasks() {
        System.out.println("Here are the tasks in your list:\n");
        for (int i = 1; i < taskList.size() + 1; i++) {
            System.out.println(i + ". " + taskList.get(i - 1));
        }
    }

    public void markTaskAsUndone(String[] words) throws DabotException{
        if (words.length < 2 || words[1].isEmpty()) {
            throw new DabotException("Task number cannot be empty!");
        }
        int taskId = Integer.parseInt(words[1]) - 1;
        if (taskId < 0 || taskId >= taskList.size()) {
            throw new DabotException("Invalid task number!");
        }
        Task task = taskList.get(taskId);
        task.markAsUndone();
        System.out.println("OK, I've marked this task as not done yet:\n");
        System.out.println(task);
    }

    public void markTaskAsDone(String[] words) throws DabotException{
        if (words.length < 2 || words[1].isEmpty()) {
            throw new DabotException("Task number cannot be empty!");
        }
        int taskId = Integer.parseInt(words[1]) - 1;
        if (taskId < 0 || taskId >= taskList.size()) {
            throw new DabotException("Invalid task number!");
        }
        Task task = taskList.get(taskId);
        task.markAsDone();
        System.out.println("Nice! I've marked this task as done:\n");
        System.out.println(task);
    }

    public void deleteTask(String[] words) throws DabotException {
        if (words.length < 2 || words[1].isEmpty()) {
            throw new DabotException("Task number cannot be empty!");
        }
        int taskId = Integer.parseInt(words[1]) - 1;
        if (taskId < 0 || taskId >= taskList.size()) {
            throw new DabotException("Invalid task number!");
        }
        Task task = taskList.get(taskId);
        taskList.remove(task);
        System.out.println("Noted. I've remove this task:\n\t" + task + "\n");
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
    }
}
