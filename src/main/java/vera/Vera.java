package vera;

import vera.ui.Ui;
import vera.core.Storage;
import vera.core.VeraException;
import vera.tasks.TaskList;

/**
 * Represents the main class for the Vera chatbot.
 * Vera is a personal assistant chatbot that helps users manage tasks such as todos, deadlines, and events.
 * It supports both text-based and graphical user interfaces (GUI).
 */
public class Vera{
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
                response = list.markTask(index);
            } else if (cmd.startsWith("unmark ")) {
                commandType = "unmark";
                int index = getIndex(cmd);
                response = list.unmarkTask(index);
            } else if (cmd.startsWith("delete ")) {
                commandType = "delete";
                int index = getIndex(cmd);
                response = list.deleteTask(index);
            } else if (cmd.startsWith("find ")) {
                commandType = "find";
                String[] keywords = cmd.split(" ", 2)[1].split("\\s");
                response = list.findTask(keywords);
            } else {
                commandType = "add";
                response = list.addTask(cmd);
            }
            ui.showMessage(response);
            ui.drawLine();
            return response;
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
        } catch (Exception e) {
            return "An unexpected error occurred. Please try again.";
        }
    }

    public static void main(String[] args) {
        Vera vera = new Vera();
        vera.run();
    }

    /**
     * Gets the type of the command executed.
     *
     * @return The type of the command executed.
     */
    public String getCommandType() {
        return commandType;
    }
}

