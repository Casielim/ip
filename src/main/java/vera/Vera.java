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
            s = ui.getNextLine();  // Ensure input is always taken after an exception
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

