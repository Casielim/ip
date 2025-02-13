package vera.tasks;

import java.util.ArrayList;
import java.util.List;

import vera.core.VeraException;

/**
 * Represent a task list.
 */
public class TaskList {
    private static List<Task> list;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param list An existing list os Task.
     */
    public TaskList(List<Task> list) {
        this.list = list;
    }

    /**
     * Adds a Task to the task list.
     *
     * @param s An user input String.
     * @return A string of added task.
     * @throws VeraException if the description is missing or the task string format is incorrect.
     */
    public String addTask(String s) throws VeraException {
        String[] part = s.split(" ", 2);
        if (part.length < 2) {
            throw new VeraException("Please add a description to your task!");
        }
        String first = part[0];
        Task task;
        switch (first) {
        case "todo":
            task = new Todo(part[1]);
            break;
        case "deadline":
            String[] partDL = part[1].split("/by ");
            task = new Deadline(partDL[0], partDL[1]);
            break;
        case "event":
            String[] partEV = part[1].split("/");
            String from = partEV[1].split(" ", 2)[1];
            String to = partEV[2].split(" ", 2)[1];
            task = new Event(partEV[0], from, to);
            break;
        default:
            throw new VeraException("I'm sorry, I can't get you, please try with command + description");
        }
        list.add(task);
        return addTaskResponse(task);
    }

    private static String addTaskResponse(Task task) {
        return String.format("  Got it. I've added this task:\n   %s\n  Now you have %d "
                        + "tasks in the list.", task, list.size());
    }

    /**
     * Shows a list of tasks stored in task list.
     *
     * @return A list of tasks in string type.
     */
    public String showList() {
        StringBuilder response = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            int num = i + 1;
            response.append("\n").append("  ").append(num).append(".").append(list.get(i).toString());
        }
        return response.toString();
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
     * @return A string to inform the user the task has already marked
     */
    public String markTask(int index) throws VeraException {
        checkValidIndex(index);
        getTask(index).markDone();
        return String.format("Nice! I've marked this task as done:\n  %s", getTask(index));
    }

    /**
     * Marks task as not yet done based on its index.
     *
     * @param index The index of task to be unmarked.
     * @return A string to inform the user the task has already unmarked.
     */
    public String unmarkTask(int index) throws VeraException {
        checkValidIndex(index);
        getTask(index).unmarkDone();
        return String.format("OK, I've marked this task as not done yet:\n  %s", getTask(index));
    }

    /**
     * Deletes a task from the task list based on its index.
     *
     * @param index The index of task to be deleted.
     * @return A string to inform the user the task has been deleted.
     */
    public String deleteTask(int index) throws VeraException {
        checkValidIndex(index);
        Task removedTask = getTask(index);
        list.remove(index);
        return String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                removedTask, list.size());
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
     * @param keywords strings use to search in a list of tasks.
     * @return A string return a list of matching task.
     */
    public String findTask(String ... keywords) {
        StringBuilder response = new StringBuilder();
        List<Task> foundedTaskList = new ArrayList<>();

        for (Task task: list) {
            if (containsKeyword(keywords, task)) {
                foundedTaskList.add(task);
            }
        }

        if (foundedTaskList.isEmpty()) {
            response.append("Can't find any matching task");
        } else {
            response.append("Here are the matching tasks in your list:");
            for (int i = 0; i < foundedTaskList.size(); i++) {
                int num = i + 1;
                response.append("\n").append("  ").append(num).append(".").append(foundedTaskList.get(i));
            }
        }
        return response.toString();
    }

    private static boolean containsKeyword(String[] keywords, Task task) {
        for (String keyword : keywords) {
            if (task.description.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
