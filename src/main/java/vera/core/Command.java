package vera.core;

/**
 * Shows different command types using enumeration.
 */
public enum Command {
    LIST,
    MARK,
    UNMARK,
    DELETE,
    FIND,
    SNOOZE,
    ADD,
    GREETING,
    OOPS;


    /**
     * Converts command string to command enum value.
     *
     * @param cmd The input command string.
     * @return The corresponding command enum value.
     */
    public static Command getCommandEnum(String cmd) {
        if (cmd.equals("list")) {
            return LIST;
        } else if (cmd.startsWith("mark")) {
            return MARK;
        } else if (cmd.startsWith("unmark")) {
            return UNMARK;
        } else if (cmd.startsWith("delete")) {
            return DELETE;
        } else if (cmd.startsWith("find")) {
            return FIND;
        } else if (cmd.startsWith("snooze")) {
            return SNOOZE;
        } else if (isTask(cmd)){
            return ADD;
        } else {
            return OOPS;
        }
    }

    private static boolean isTask(String cmd) {
        if (cmd.startsWith("todo") || cmd.startsWith("deadline") || cmd.startsWith("event")) {
            return true;
        }
        return false;
    }
}
