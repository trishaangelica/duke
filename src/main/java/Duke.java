import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;

public class Duke {

    public static void printTaskList(String[] taskList){
        int counter = 1;
        System.lineSeparator();
        System.out.println("Currently, you have these items in your list: \n");
        for(String task: taskList){
             System.out.println(counter + ". " + task);
            counter++;
        }

    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|";
        String line = "\n_______________________________________________\n";
        String input;
        String[] taskList = new String[100];
        int matchcount = 0;
        System.out.println("Hello from\n" + logo);
        System.out.print(line + "Hello! I'm Duke\n" + "What can I do for you?" + line);
        Scanner scan = new Scanner(System.in);
        while(true) {

            input = scan.nextLine();

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.print(line);
                taskList = Arrays.stream(taskList).filter(Objects::nonNull).toArray(String[]::new); //remove null values of array
                printTaskList(taskList);
                System.out.print(line);

            } else {
                taskList[matchcount] = input;
                matchcount++;

                System.out.print(line + "added: " + input + line);

            }
        }
        System.out.print(line + "Bye. Hope to see you again soon!" + line);
        System.exit(0);
    }
}

