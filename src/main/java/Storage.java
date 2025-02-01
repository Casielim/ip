import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    private static final String FILE_PATH = "./data/Vera.txt";
    private File file;

    public Storage() {
        this.file = new File(FILE_PATH);
    }

    public void checkFile() {
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void saveToFile(List<Task> tasks) {
        try {
            checkFile();
            FileWriter fw = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                fw.write(task.toFileString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void saveToExistingFile(Task task) throws IOException {
        try {
            checkFile();
            FileWriter fw = new FileWriter(FILE_PATH, true);
            fw.write(task.toFileString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Task> loadFileContent() {
        List<Task> list = new ArrayList<>();
        try {
            checkFile();
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                try {
                    list.add(Parser.convertTextToTask(s));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error file not found :" + e.getMessage());
        }
        return list;
    }
}
