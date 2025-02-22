package vera.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vera.tasks.Task;
import vera.tasks.TaskList;

/**
 * Represents a storage storing the task list.
 */
public class Storage {
    private static final String FILE_PATH = "./data/Vera.txt";
    private File file;

    /**
     * Constructs a Storage object.
     * Initializes the file.
     */
    public Storage() {
        this.file = new File(FILE_PATH);
    }

    /**
     * Ensures that the storage file exists and in the correct directory.
     * Creates parent directory if needed.
     * Creates a new storage file if the directory do not have such file.
     */
    public void checkFile() throws VeraException {
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new VeraException("Oops: " + e.getMessage());
            }
        }
    }

    /**
     * Saves current task list to the storage file.
     *
     * @param tasks The TaskList object containing a list of task to be saved.
     */
    public void saveToFile(TaskList tasks) throws VeraException {
        try {
            checkFile();
            FileWriter fw = new FileWriter(FILE_PATH);
            for (Task task : tasks.getList()) {
                fw.write(task.toFileString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new VeraException("Fail writing to file: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file and return them.
     * Each line is converted to one Task object.
     * Invalid lines are skipped.
     *
     * @return A list of Task object.
     * @throws VeraException If the storage file does not exist.
     */
    public List<Task> loadFileContent() throws VeraException {
        List<Task> list = new ArrayList<>();
        Scanner sc;
        try {
            checkFile();
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new VeraException("file not found");
        }

        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            try {
                list.add(Parser.convertTextToTask(s));
            } catch (IllegalArgumentException e) {
                throw new VeraException(e.getMessage());
            }
        }
        sc.close();
        return list;
    }
}
