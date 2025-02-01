import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Vera{
    private static List<Task> list = new ArrayList<>();
    private static final String FILE_PATH = "./data/Vera.txt";
    private static final File FILE = new File(FILE_PATH);
    private Ui ui;
    private Storage storage;

    public Vera() {
        this.ui = new Ui();
        this.storage = new Storage();
    }

    public void run() {
        ui.greetings();
        list = storage.loadFileContent();

        String s = ui.getNextLine();
        while (!s.equals("bye")) {
            if (s.equals("clear")) {
                list.clear();
                showlist(list);
                s = ui.getNextLine();
                continue;
            }

            //Showing list
            if (s.equals("list")) {
                showlist(list);
                storage.saveToFile(list);
                s = ui.getNextLine();
                continue;
            }

            //mark command should be mark + an int
            //mark + string should be a task eg. mark paper
            if (isMarkInteger(s)) {
                storage.saveToFile(list);
                s = ui.getNextLine();
                continue;
            }

            //unmark command should be mark + an int
            //unmark + string should be a task eg. unmark things
            if (isUnmarkInteger(s)) {
                storage.saveToFile(list);
                s = ui.getNextLine();
                continue;
            }

            //Delete task
            if (isDeleteInteger(s)) {
                storage.saveToFile(list);
                s = ui.getNextLine();
                continue;
            }

            //adding task to list and catch exception
            try {
                addTask(s);
                storage.saveToFile(list);
            } catch (VeraException e) {
                System.out.println("  Error: " + e.getMessage());
                ui.drawLine();
            }

            s = ui.getNextLine();
        }
        ui.exit();
    }

    private void addTask(String s) throws VeraException {
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
                ui.drawLine();
                break;

            case "deadline":
                if (part.length < 2) {
                    throw new VeraException("Please add description to your task!");
                }

                String[] partDL = part[1].split("/by ");
                Task dl = new Deadline(partDL[0], partDL[1]);
                list.add(dl);
                System.out.println(addTaskResponse(dl));
                ui.drawLine();
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
                ui.drawLine();
                break;

            default:
                throw new VeraException("I'm sorry, I can't get you, please try with command + description");
        }
    }

    private static String addTaskResponse(Task task) {
        return String.format("  Got it. I've added this task:\n   %s\n  Now you have %d tasks in the list.",
                task, list.size());
    }

    private void showlist(List ls) {
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < ls.size(); i++) {
            int num = i + 1;
            System.out.println("  " + num + "." + ls.get(i).toString());
        }
        ui.drawLine();
    }

    private boolean isMarkInteger(String s) {
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
                    ui.drawLine();
                    return true;
                }

                Task t = list.get(i - 1);
                t.markFeature();
                ui.drawLine();
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private boolean isUnmarkInteger(String s) {
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
                    ui.drawLine();
                    return true;
                }

                Task t = list.get(i - 1);
                t.unmarkFeature();
                ui.drawLine();
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private boolean isDeleteInteger(String s) {
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
                    ui.drawLine();
                    return true;
                }

                int index = i - 1;
                Task task = list .get(index);
                System.out.println(String.format("  Noted. I've removed this task:\n   %s\n  Now you have %d tasks in the list.",
                        task, list.size() - 1));
                list.remove(index);

                ui.drawLine();
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

    public static void main(String[] args) {
        Vera vera = new Vera();
        vera.run();
    }
}

