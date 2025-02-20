package vera;

import vera.core.Command;
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

    /**
     * Constructs a Vera Chatbot instance.
     * Initializes ui, storage as well as a task list with content loaded from storage file.
     * Initializes a new empty task list if error occur loading the content.
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
     * Runs the chatbot to allow users input their command.
     * Chatbot will keep running until the "bye" command is detected.
     */
    public void run() {
        ui.greetings();
        String s = ui.getNextLine();
        while (!s.equals("bye")) {
            try {
                processCommand(s);
                storage.saveToFile(list);
            } catch (VeraException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Oops: Unexpected error: " + e.getMessage());
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
     * @throws VeraException If they catch
     */
    public String processCommand(String cmd) throws VeraException {
        if (!isValidCommand(cmd)) {
            return "Oops sorry, I can't get you.";
        }
        try {
            Command commandEnum = Command.getCommandEnum(cmd);
            String response = executeCommand(cmd, commandEnum);
            ui.showMessage(response);
            ui.drawLine();
            return response;
        } catch (AssertionError e) {
            ui.showError("Oops assertion failed: " + e.getMessage());
            ui.drawLine();
            return executeCommand("Oops assertion failed: " + e.getMessage(), Command.OOPS);
        } catch (NumberFormatException e) {
            ui.showError("Oops: " + e.getMessage() + " use only index");
            ui.drawLine();
            return executeCommand("Oops: " + e.getMessage() + " use only index", Command.OOPS);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showError("Oops: " + e.getMessage());
            ui.drawLine();
            return executeCommand("Oops: " + e.getMessage(), Command.OOPS);
        } catch (VeraException e) {
            ui.showError(e.getMessage());
            ui.drawLine();
            return executeCommand("Oops: " + e.getMessage(), Command.OOPS);
        }
    }

    private boolean isValidCommand(String cmd) {
        if (Command.getCommandEnum(cmd).equals(Command.OOPS)) {
            return false;
        }
        return true;
    }

    private String executeCommand(String cmd, Command commandEnum) throws VeraException {
        int index;
        switch (commandEnum) {
        case LIST:
            return list.showList();
        case MARK:
            index = getIndex(cmd);
            assert index >= 0 : "Index must be greater than 0";
            return list.markTask(index);
        case UNMARK:
            index = getIndex(cmd);
            assert index >= 0 : "Index must be greater than 0";
            return list.unmarkTask(index);
        case DELETE:
            index = getIndex(cmd);
            assert index >= 0 : "Index must be greater than 0";
            return list.deleteTask(index);
        case FIND:
            assert cmd.split("\\s").length > 1 : "Must have at least one keyword";
            String[] keywords = cmd.split(" ", 2)[1].split("\\s");
            return list.findTask(keywords);
        case SNOOZE:
            index = getIndex(cmd);
            return doSnooze(cmd, index);
        case ADD:
            return list.addTask(cmd);
        default:
            return cmd;
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
            response = "Oops! " +
                    "Invalid snooze format. Use:\n" +
                    "  - For deadlines: snooze <index> <newTime>\n" +
                    "  - For events: snooze <index> <newFrom> <newTo>";
        }
        return response;
    }

    private int getIndex(String cmd) throws NumberFormatException, VeraException {
        try {
            return Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new VeraException("please enter index that you want to work on, use <command> <index> " +
                    "or find <keywords>");
        }
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input User input.
     * @return The response from the chatbot.
     */
    public String getResponse(String input) {
        try {
            String response = processCommand(input);
            storage.saveToFile(list);
            return response;
        } catch (VeraException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Oops: An unexpected error occurred. Please try again.";
        }
    }

    public static void main(String[] args) {
        Vera vera = new Vera();
        vera.run();
    }
}

