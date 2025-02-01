import java.util.Scanner;

public class Ui {
    public static final String LINE = "  ________________________________________________________________________";
    private String greetings = "  Hello! I'm Vera\n  What can I do for you?";
    private String bye = "  Bye. Hope to see you again soon!";
    Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }
    public void greetings() {
        System.out.println(LINE);
        System.out.println(greetings);
        System.out.println(LINE);
    }

    public void exit() {
        System.out.println(bye);
        System.out.println(LINE);
    }

    public String getNextLine() {
        return sc.nextLine();
    }

    public void drawLine() {
        System.out.println(LINE);
    }

}
