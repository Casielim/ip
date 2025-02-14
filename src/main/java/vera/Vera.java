package vera;

import vera.core.Storage;
import vera.core.VeraException;
import vera.tasks.TaskList;
import vera.ui.Ui;

/**
 * Represents the main class for the Vera chatbot.
 * Vera is a personal assistant chatbot that helps users manage tasks such as todos, deadlines, and events.
 * It supports both text-based and graphical user interfaces (GUI).
 */
public class Vera {
    private Ui ui;
    private Storage storage;
    private TaskList list;
    private String commandType;

    /**
     * Construct a Vera Chatbot instance.
     * Initialize ui, storage as well as a task list with content loaded from storage file.
     * Initialized a new empty task list if error occur loading the content.
     */
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

    /**
     * Run the chatbot to allow users input their command.
     * Chatbot will keep running until the "bye" command is detected.
     */
    public void run() {
        ui.greetings();
        String s = ui.getNextLine();
        while (!s.equals("bye")) {
            try {
                executeCommand(s);
                storage.saveToFile(list);
            } catch (VeraException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            }
            s = ui.getNextLine();
        }
        ui.exit();
    }

    /**
     * Executes a user command and returns a response as a string.
     *
     * @param cmd User command.
     * @return The response after executing the command.
     */
    public String executeCommand(String cmd) {
        try {
            String response;
            if (cmd.equals("list")) {
                commandType = "list";
                response = list.showList();
            } else if (cmd.startsWith("mark ")) {
                commandType = "mark";
                int index = getIndex(cmd);
                assert index >= 1 : "Index must be greater than 0";
                response = list.markTask(index);
            } else if (cmd.startsWith("unmark ")) {
                commandType = "unmark";
                int index = getIndex(cmd);
                assert index >= 1 : "Index must be greater than 0";
                response = list.unmarkTask(index);
            } else if (cmd.startsWith("delete ")) {
                commandType = "delete";
                int index = getIndex(cmd);
                assert index >= 1 : "Index must be greater than 0";
                response = list.deleteTask(index);
            } else if (cmd.startsWith("find ")) {
                commandType = "find";
                String[] keywords = cmd.split(" ", 2)[1].split("\\s");
                assert keywords.length >= 1 : "Must have at least one keyword";
                response = list.findTask(keywords);
            } else if (cmd.startsWith("snooze ")) {
                commandType = "snooze";
                int index = getIndex(cmd);
                response = doSnooze(cmd, index);
            } else {
                commandType = "add";
                response = list.addTask(cmd);
            }
            ui.showMessage(response);
            ui.drawLine();
            return response;
        } catch (AssertionError e) {
            commandType = "error";
            return "Assertion failed: " + e.getMessage();
        } catch (NumberFormatException e) {
            commandType = "error";
            ui.showError(e.getMessage() + " use only index");
            ui.drawLine();
            return e.getMessage() + " use only index";
        } catch (VeraException e) {
            ui.showError(e.getMessage());
            ui.drawLine();
            return "Oops: " + e.getMessage();
        }
    }

    private String doSnooze(String command, int index) throws VeraException {
        String response;
        String[] parts = command.split(" ");
        if (parts.length == 4) {  // Deadline
            String newBy = parts[2] + " " + parts[3];
            response = list.snoozeTask(index, newBy);
        } else if (parts.length == 6) {  // Event
            String newFrom = parts[2] + " " + parts[3];
            String newTo = parts[4] + " " + parts[5];
            response = list.snoozeTask(index, newFrom, newTo);
        } else {
            response = "Invalid snooze format. Use:\n" +
                    "  - For deadlines: snooze <index> <newTime>\n" +
                    "  - For events: snooze <index> <newFrom> <newTo>";
        }
        return response;
    }

    private static int getIndex(String cmd) {
        return Integer.parseInt(cmd.split(" ")[1]) - 1;
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input User input.
     * @return The response from the chatbot.
     */
    public String getResponse(String input) {
        try {
            String response = executeCommand(input);
            storage.saveToFile(list);
            return response;
        } catch (VeraException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred. Please try again.";
        }
    }

    /**
     * Gets the type of the command executed.
     *
     * @return The type of the command executed.
     */
    public String getCommandType() {
        return commandType;
    }

    public static void main(String[] args) {
        Vera vera = new Vera();
        vera.run();
    }
}

