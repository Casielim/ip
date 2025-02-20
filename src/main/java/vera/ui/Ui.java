package vera.ui;

import java.util.Scanner;

/**
 * Represents users interface.
 */
public class Ui {
    public static final String LINE = "  ________________________________________________"
            + "________________________";
    protected final String greetings = "  Hello! I'm Vera\n  What can I do for you?";
    protected final String bye = "  Bye. Hope to see you again soon!";
    private final Scanner sc;

    /**
     * Constructs an ui and initializes a scanner.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Shows the greetings line.
     * Displays a line before and after the greetings.
     */
    public void greetings() {
        System.out.println(LINE);
        System.out.println(greetings);
        System.out.println(LINE);
    }

    /**
     * Shows the goodbye line.
     * Displays a line after the exit line.
     */
    public void exit() {
        System.out.println(bye);
        System.out.println(LINE);
    }

    /**
     * Reads and returns the next line user input.
     *
     * @return The next line input from the scanner.
     */
    public String getNextLine() {
        return sc.nextLine();
    }

    /**
     * Prints a given message.
     *
     * @param msg The message to be displayed.
     */
    public void showMessage(String msg) {
        System.out.println("  " + msg);
    }

    /**
     * Prints a horizontal line.
     */
    public void drawLine() {
        System.out.println(LINE);
    }


    /**
     * Prints an error message.
     *
     * @param msg The description of the error wanted to print.
     */
    public void showError(String msg) {
        System.out.println("  Oops: " + msg);
    }

}
