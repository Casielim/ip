package vera;

import vera.core.Ui;
import vera.core.Storage;
import vera.core.VeraException;
import vera.tasks.TaskList;

public class Vera{
    private Ui ui;
    private Storage storage;
    private TaskList list;

    public Vera() {
        this.ui = new Ui();
        this.storage = new Storage();
        try {
            this.list = new TaskList(storage.loadFileContent(), ui);
        } catch (VeraException e) {
            ui.showError(e.getMessage());
            this.list = new TaskList(ui);
        }
    }

    public void run() {
        ui.greetings();

        String s = ui.getNextLine();
        while (!s.equals("bye")) {
            try {
                executeCommand(s);
                storage.saveToFile(list);
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            }
            s = ui.getNextLine();  // Ensure input is always taken after an exception
        }
        ui.exit();
    }

    public void executeCommand(String cmd) {
        try {
            if (cmd.equals("list")) {
                list.showList();
            } else if (cmd.startsWith("mark ")) {
                int index = Integer.parseInt(cmd.split(" ")[1]) - 1;
                list.markTask(index);
            } else if (cmd.startsWith("unmark ")) {
                int index = Integer.parseInt(cmd.split(" ")[1]) - 1;
                list.unmarkTask(index);
            } else if (cmd.startsWith("delete ")) {
                int index = Integer.parseInt(cmd.split(" ")[1]) - 1;
                list.deleteTask(index);
            } else if (cmd.startsWith("find ")) {
                String keyword = cmd.split(" ", 2)[1];
                list.findTask(keyword);
            } else {
                list.addTask(cmd);
            }
        } catch (NumberFormatException e) {
            ui.showError(e.getMessage() + " use only index");
        } catch (VeraException e) {
            ui.showError(e.getMessage());
        }
        ui.drawLine();
    }

    public static void main(String[] args) {
        Vera vera = new Vera();
        vera.run();
    }
}

