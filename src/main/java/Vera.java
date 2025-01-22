import java.util.Scanner;

public class Vera{
    private static final String line = "  ______________________________________________________";

    public static void main(String[] args) {
        String greetings = "  Hello! I'm Vera\n  What can I do for you?";
        String bye = "  Bye. Hope to see you again soon!";

        Scanner sc = new Scanner(System.in);

        //greetings part
        System.out.println(line);
        System.out.println(greetings);
        System.out.println(line);

        //echo part
        String s = sc.nextLine();
        while (!s.equals("bye")) {
            System.out.println("  " + s);
            System.out.println(line);
            s = sc.nextLine();
        }

        //exit part
        System.out.println(bye);
        System.out.println(line);
    }
}

