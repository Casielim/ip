import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Vera{
    private static final String line = "  ________________________________________________________________________";
    private static List<Task> list = new ArrayList<>();
    private static final String FILE_PATH = "./data/Vera.txt";
    private static final File FILE = new File(FILE_PATH);

    private static void addTask(String s) throws VeraException {
        String[] part = s.split(" ",2);
        String first = part[0];
        switch (first) {
            case "todo":
                if (part.length < 2) {
                    throw new VeraException("Please add description to your task!");
                }

                Task td = new Todo(part[1]);
                list.add(td);
                System.out.println(addTaskResponse(td));
                System.out.println(line);
                break;

            case "deadline":
                if (part.length < 2) {
                    throw new VeraException("Please add description to your task!");
                }

                String[] partDL = part[1].split("/by ");
                Task dl = new Deadline(partDL[0], partDL[1]);
                list.add(dl);
                System.out.println(addTaskResponse(dl));
                System.out.println(line);
                break;

            case "event":
                if (part.length < 2) {
                    throw new VeraException("Please add description to your task!");
                }

                String[] partEV = part[1].split("/");
                String from = partEV[1].split(" ", 2)[1];
                String to = partEV[2].split(" ", 2)[1];
                Task ev = new Event(partEV[0], from, to);
                list.add(ev);
                System.out.println(addTaskResponse(ev));
                System.out.println(line);
                break;

            default:
                throw new VeraException("I'm sorry, I can't get you, please try with command + description");
        }
    }

    private static String addTaskResponse(Task task) {
        return String.format("  Got it. I've added this task:\n   %s\n  Now you have %d tasks in the list.",
                task, list.size());
    }

    private static void showlist(List ls) {
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < ls.size(); i++) {
            int num = i + 1;
            System.out.println("  " + num + "." + ls.get(i).toString());
        }
        System.out.println(line);
    }

    private static boolean isMarkInteger(String s) {
        String[] part = s.split(" ");
        if (part.length != 2) {
            return false;
        }

        if (part[0].equals("mark")) {
            try {
                int i = Integer.parseInt(part[1]);
                try {
                    checkValidIndex(i);
                } catch (VeraException e) {
                    System.out.println(e.getMessage());
                    System.out.println(line);
                    return true;
                }

                Task t = list.get(i - 1);
                t.markFeature();
                System.out.println(line);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private static boolean isUnmarkInteger(String s) {
        String[] part = s.split(" ");
        if (part.length != 2) {
            return false;
        }

        if (part[0].equals("unmark")) {
            try {
                int i = Integer.parseInt(part[1]);
                try {
                    checkValidIndex(i);
                } catch (VeraException e) {
                    System.out.println(e.getMessage());
                    System.out.println(line);
                    return true;
                }

                Task t = list.get(i - 1);
                t.unmarkFeature();
                System.out.println(line);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private static boolean isDeleteInteger(String s) {
        String[] part = s.split(" ");
        if (part.length != 2) {
            return false;
        }

        if (part[0].equals("delete")) {
            try {
                int i = Integer.parseInt(part[1]);
                try {
                    checkValidIndex(i);
                } catch (VeraException e) {
                    System.out.println(e.getMessage());
                    System.out.println(line);
                    return true;
                }

                int index = i - 1;
                Task task = list .get(index);
                System.out.println(String.format("  Noted. I've removed this task:\n   %s\n  Now you have %d tasks in the list.",
                        task, list.size() - 1));
                list.remove(index);

                System.out.println(line);
                return true;
            } catch (NumberFormatException e){
                return false;
            }
        }
        return false;
    }

    private static void checkValidIndex(int num) throws VeraException {
        if (num > list.size()) {
            throw new VeraException(String.format("  You can't do this. You have only %d tasks now.", list.size()));
        }
    }

    public static void checkFile() {
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (!FILE.exists()) {
            try {
                FILE.createNewFile();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void saveToFile(List<Task> tasks) {
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

    private static void saveToExistingFile(Task task) throws IOException {
        try {
            checkFile();
            FileWriter fw = new FileWriter(FILE_PATH, true);
            fw.write(task.toFileString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void loadFileContent() {
        try {
            checkFile();
            Scanner sc = new Scanner(FILE);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                try {
                    list.add(convertTextToTask(s));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error file not found :" + e.getMessage());
        }

    }

    public static Task convertTextToTask(String taskText) throws IllegalArgumentException {
        String[] part = taskText.split(" \\| ");
        if (part.length < 3) {
            throw new IllegalArgumentException("Error: convert text to task unsuccessful");
        }
        String type = part[0];
        boolean isDone = part[1].equals("1");
        String description = part[2];

        switch (type) {
        case "T":
            Task td = new Todo(description);
            td.isDone = isDone;
            return td;
        case "D":
            if (part.length < 4) {
                throw new IllegalArgumentException("Error: convert text to Deadline task unsuccessful");
            }
            String by = part[3];
            try {
                Task dl = new Deadline(description, by);
                dl.isDone = isDone;
                return dl;
            } catch (VeraException e) {
                System.out.println(e.getMessage());
            }
        case "E":
            if (part.length < 5) {
                throw new IllegalArgumentException("Error: convert text to Event task unsuccessful");
            }
            String from = part[3];
            String to = part[4];
            try {
                Task ev = new Event(description, from, to);
                ev.isDone = isDone;
                return ev;
            } catch (VeraException e) {
                System.out.println(e.getMessage());
            }

        default:
            throw new IllegalArgumentException("Error: Invalid task type");
        }
    }

    public static void main(String[] args) {
        String greetings = "  Hello! I'm Vera\n  What can I do for you?";
        String bye = "  Bye. Hope to see you again soon!";
        loadFileContent();
        Scanner sc = new Scanner(System.in);

        //greetings part
        System.out.println(line);
        System.out.println(greetings);
        System.out.println(line);

        String s = sc.nextLine();
        while (!s.equals("bye")) {
            //Showing list
            if (s.equals("list")) {
                showlist(list);
                s = sc.nextLine();
                continue;
            }

            //mark command should be mark + an int
            //mark + string should be a task eg. mark paper
            if (isMarkInteger(s)) {
                saveToFile(list);
                s = sc.nextLine();
                continue;
            }

            //unmark command should be mark + an int
            //unmark + string should be a task eg. unmark things
            if (isUnmarkInteger(s)) {
                saveToFile(list);
                s = sc.nextLine();
                continue;
            }

            //Delete task
            if (isDeleteInteger(s)) {
                saveToFile(list);
                s = sc.nextLine();
                continue;
            }

            //adding task to list and catch exception
            try {
                addTask(s);
                saveToFile(list);
            } catch (VeraException e) {
                System.out.println("  Error: " + e.getMessage());
                System.out.println(line);
            }

            s = sc.nextLine();
        }

        //exit part
        System.out.println(bye);
        System.out.println(line);
    }
}

