import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vera{
    private static final String line = "  ______________________________________________________";
    private static List<Task> list = new ArrayList<>();

    private static void echo(String s) {
        System.out.println("  added: " + s);
        System.out.println(line);
    }

    private static void showlist(List ls) {
        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < ls.size(); i++) {
            int num = i + 1;
            System.out.println("  " + num + "." + ls.get(i).toString());
        }
        System.out.println(line);
    }

    private static boolean isMarkInteger(String s) {
        String[] part = s.split(" ");
        if (part.length != 2) {
            return false;
        }

        if (part[0].equals("mark")) {
            try {
                int i = Integer.parseInt(part[1]);
                Task t = list.get(i - 1);
                t.markFeature();
                System.out.println(line);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private static boolean isUnmarkInteger(String s) {
        String[] part = s.split(" ");
        if (part.length != 2) {
            return false;
        }

        if (part[0].equals("unmark")) {
            try {
                int i = Integer.parseInt(part[1]);
                Task t = list.get(i - 1);
                t.unmarkFeature();
                System.out.println(line);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
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

            //mark command should be mark + an int
            //mark + string should be a task eg. mark paper
            if (isMarkInteger(s)) {
                s = sc.nextLine();
                continue;
            }

            //unmark command should be mark + an int
            //unmark + string should be a task eg. unmark things
            if (isUnmarkInteger(s)) {
                s = sc.nextLine();
                continue;
            }

            Task task = new Task(s);
            list.add(task);
            echo(s);
            s = sc.nextLine();
        }

        //exit part
        System.out.println(bye);
        System.out.println(line);
    }
}

