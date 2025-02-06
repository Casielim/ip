package vera.tasks;

import java.util.ArrayList;
import java.util.List;

import vera.core.Ui;
import vera.core.VeraException;

/**
 * Represent a task list.
 */
public class TaskList {
    private static List<Task> list;
    private final Ui ui;

    /**
     * Constructs an empty TaskList.
     *
     * @param ui The UI instance used for display message.
     */
    public TaskList(Ui ui) {
        this.ui = ui;
        this.list = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param list An existing list os Task.
     * @param ui The UI instance used for display message.
     */
    public TaskList(List<Task> list, Ui ui) {
        this.list = list;
        this.ui = ui;
    }

    /**
     * Adds a Task to the task list.
     *
     * @param s An user input String.
     * @throws VeraException if the description is missing or the task string format is incorrect.
     */
    public void addTask(String s) throws VeraException {
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
            break;

        case "deadline":
            if (part.length < 2) {
                throw new VeraException("Please add description to your task!");
            }

            String[] partDL = part[1].split("/by ");
            Task dl = new Deadline(partDL[0], partDL[1]);
            list.add(dl);
            System.out.println(addTaskResponse(dl));
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
            break;

        default:
            throw new VeraException("I'm sorry, I can't get you, please try with command + description");
        }
    }

    private static String addTaskResponse(Task task) {
        return String.format("  Got it. I've added this task:\n   %s\n  Now you have %d "
                        + "tasks in the list.", task, list.size());
    }

    /**
     * Shows a list of tasks stored in task list.
     */
    public void showList() {
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            int num = i + 1;
            System.out.println("  " + num + "." + list.get(i).toString());
        }
    }


    private static void checkValidIndex(int index) throws VeraException {
        if (index + 1 > list.size()) {
            throw new VeraException(String.format("You can't do this. You have only %d tasks now.",
                    list.size()));
        }
    }

    /**
     * Marks task as done based on its index.
     *
     * @param index The index of to be mark as done.
     */
    public void markTask(int index) {
        try {
            checkValidIndex(index);
            getTask(index).markFeature();
        } catch (VeraException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Marks task as not yet done based on its index.
     *
     * @param index The index of task to be unmarked.
     */
    public void unmarkTask(int index) {
        try {
            checkValidIndex(index);
            getTask(index).unmarkFeature();
        } catch (VeraException e) {
            ui.showError(e.getMessage());
        }

    }

    /**
     * Deletes a task from the task list based on its index.
     *
     * @param index The index of task to be deleted.
     */
    public void deleteTask(int index) {
        try {
            checkValidIndex(index);
            System.out.println(String.format("  Noted. I've removed this task:\n   %s\n  "
                    + "Now you have %d tasks in the list.", getTask(index), list.size() - 1));
            list.remove(index);
        } catch (VeraException e) {
            ui.showError(e.getMessage());
        }
    }

    public List<Task> getList() {
        return list;
    }

    public Task getTask(int index) {
        return list.get(index);
    }

    /**
     * Finds tasks that contain the keyword.
     *
     * @param keyWord string use to search in a list of tasks.
     */
    public void findTask(String keyWord) {
        List<Task> foundedTaskList = new ArrayList<>();

        for (Task task: list) {
            if (task.description.contains(keyWord)) {
                foundedTaskList.add(task);
            }
        }

        if (foundedTaskList.size() == 0) {
            System.out.println("Can't find any matching task");
        } else {
            System.out.println("  Here are the matching in your list:");
            for (int i = 0; i < foundedTaskList.size(); i++) {
                int num = i + 1;
                System.out.println("  " + num + "." + foundedTaskList.get(i).toString());
            }
        }
    }
}
