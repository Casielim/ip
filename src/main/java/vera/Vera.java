package vera;

import vera.ui.Ui;
import vera.core.Storage;
import vera.core.VeraException;
import vera.tasks.TaskList;

public class Vera{
    private Ui ui;
    private Storage storage;
    private TaskList list;
    private String commandType;

    public Vera() {
        this.ui = new Ui();
        this.storage = new Storage();
        try {
            this.list = new TaskList(storage.loadFileContent());
        } catch (VeraException e) {
            ui.showError(e.getMessage());
            this.list = new TaskList();
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

    public String executeCommand(String cmd) {
        try {
            if (cmd.equals("list")) {
                commandType = "list";
                String response = list.showList();
                return response;
            } else if (cmd.startsWith("mark ")) {
                commandType = "mark";
                int index = Integer.parseInt(cmd.split(" ")[1]) - 1;
                String response = list.markTask(index);
                return response;
            } else if (cmd.startsWith("unmark ")) {
                commandType = "unmark";
                int index = Integer.parseInt(cmd.split(" ")[1]) - 1;
                String response = list.unmarkTask(index);
                return response;
            } else if (cmd.startsWith("delete ")) {
                commandType = "delete";
                int index = Integer.parseInt(cmd.split(" ")[1]) - 1;
                String response = list.deleteTask(index);
                return response;
            } else if (cmd.startsWith("find ")) {
                commandType = "find";
                String keyword = cmd.split(" ", 2)[1];
                String response = list.findTask(keyword);
                return response;
            } else {
                commandType = "add";
                String response = list.addTask(cmd);
                return response;
            }
        } catch (NumberFormatException e) {
            commandType = "error";
            ui.showError(e.getMessage() + " use only index");
            return e.getMessage() + " use only index";
        } catch (VeraException e) {
            ui.showError(e.getMessage());
            return "Oops: " + e.getMessage();
        }
        //ui.drawLine();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            String response = executeCommand(input);
            storage.saveToFile(list);
            return response;
            //return "Successfully processed: " + commandType;
        } catch (Exception e) {
            return "An unexpected error occurred. Please try again.";
        }
    }

    public static void main(String[] args) {
        Vera vera = new Vera();
        vera.run();
    }

    public String getCommandType() {
        return commandType;
    }
}

