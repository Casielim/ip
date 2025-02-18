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
    GREETING;


    /**
     * Converts command string to command enum value.
     *
     * @param cmd The input command string
     * @return The corresponding command enum value.
     */
    public static Command getCommandEnum(String cmd) {
        if (cmd.equals("list")) {
            return LIST;
        } else if (cmd.startsWith("mark ")) {
            return MARK;
        } else if (cmd.startsWith("unmark ")) {
            return UNMARK;
        } else if (cmd.startsWith("delete ")) {
            return DELETE;
        } else if (cmd.startsWith("find ")) {
            return FIND;
        } else if (cmd.startsWith("snooze ")) {
            return SNOOZE;
        } else {
            return ADD;
        }
    }
}
