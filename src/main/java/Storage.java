import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    public static ArrayList<Task> taskList;
    public static final String FILE_PATH = "data/dabot.txt";

    public Storage(){
        taskList = new ArrayList<>(100);
        loadTasks();
    }

    public void addTask(Task task) {
        taskList.add(task);
        System.out.println("Got it. I've added this task:\n\t" + task + "\n");
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        saveTasks();
    }

    public void printTasks() {
        System.out.println("Here are the tasks in your list:\n");
        for (int i = 1; i < taskList.size() + 1; i++) {
            System.out.println(i + ". " + taskList.get(i - 1));
        }
    }

    public void markTaskAsUndone(String[] words) throws DabotException{
        if (words.length < 2 || words[1].isEmpty()) { //argument check
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
        saveTasks();
    }

    public void markTaskAsDone(String[] words) throws DabotException{
        if (words.length < 2 || words[1].isEmpty()) { //argument check
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
        saveTasks();
    }

    public void deleteTask(String[] words) throws DabotException {
        if (words.length < 2 || words[1].isEmpty()) { //argument check
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
        saveTasks();
    }

    public void saveTasks() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file);
            for (Task task : taskList) {
                fw.write(task.encodeString() + '\n');
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void loadTasks() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                Task task = Task.decodeString(line);
                taskList.add(task);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        } catch (DabotException e) {
            System.out.println(e.getMessage());
        }
    }
}
