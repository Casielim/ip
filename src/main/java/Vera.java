import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vera{
    private static final String line = "  ______________________________________________________";
    private static List<String> list = new ArrayList<>();

    private static void echo(String s) {
        System.out.println("  added:" + s);
        list.add(s);
        System.out.println(line);
    }

    private static void showlist(List ls) {
        for (int i = 0; i < ls.size(); i++) {
            int num = i + 1;
            System.out.println("  " + num + ". " + ls.get(i));
        }
        System.out.println(line);
    }

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
        while (true) {
            if (s.equals("bye")) {
                break;
            }

            //Showing list 
            if (s.equals("list")) {
                showlist(list);
                s = sc.nextLine();
                continue;
            }
            echo(s);
            s = sc.nextLine();
        }

        //exit part
        System.out.println(bye);
        System.out.println(line);
    }
}

