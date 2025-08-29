package dabot.io;

import dabot.main.DabotException;
import dabot.task.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /** Loads tasks from file. Returns an empty list if file is absent. */
    public List<Task> load() throws DabotException {
        List<Task> list = new ArrayList<>();
        File f = new File(filePath);
        try {
            File parent = f.getParentFile();
            if (parent != null) parent.mkdirs();
            if (!f.exists()) return list;

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

    /** Saves all tasks into file, one per line. */
    public void save(List<Task> tasks) throws DabotException {
        File f = new File(filePath);
        try {
            File parent = f.getParentFile();
            if (parent != null) parent.mkdirs();
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
