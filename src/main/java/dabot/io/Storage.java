package dabot.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dabot.main.DabotException;
import dabot.task.Task;
/**
 * Handles saving and loading of {@link Task} objects to and from the disk.
 * <p>
 * The {@code Storage} class abstracts away file I/O details, ensuring that
 * tasks can be persisted across program runs. Tasks are stored one per line
 * in a plain text file and encoded/decoded using {@link Task#encodeString()}
 * and {@link Task#decodeString(String)}.
 * </p>
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a {@code Storage} object to manage a given file path.
     *
     * @param filePath the path to the file where tasks will be saved/loaded
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * <p>
     * Each line of the file is decoded into a {@link Task}. Invalid lines
     * are skipped with a warning. If the file does not exist, an empty list
     * is returned.
     * </p>
     *
     * @return a list of tasks loaded from file; empty if file is missing
     * @throws DabotException if the file cannot be read due to I/O issues
     */
    public List<Task> load() throws DabotException {
        List<Task> list = new ArrayList<>();
        File f = new File(filePath);
        try {
            File parent = f.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            if (!f.exists()) {
                return list;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        list.add(Task.decodeString(line));
                    } catch (DabotException e) {
                        System.out.println("Skipping bad task line: " + e.getMessage());
                    }
                }
            }
            return list;
        } catch (IOException e) {
            throw new DabotException("Failed to load tasks: " + e.getMessage());
        }
    }

    /**
     * Saves a list of tasks into the storage file.
     * <p>
     * Each task is written on a new line using its encoded string form.
     * Creates the file and parent directories if they do not exist.
     * </p>
     *
     * @param tasks the list of tasks to save
     * @throws DabotException if the tasks cannot be saved due to I/O issues
     */
    public void save(List<Task> tasks) throws DabotException {
        File f = new File(filePath);
        try {
            File parent = f.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            try (FileWriter fw = new FileWriter(f)) {
                for (Task t : tasks) {
                    fw.write(t.encodeString());
                    fw.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new DabotException("Failed to save tasks: " + e.getMessage());
        }
    }
}
