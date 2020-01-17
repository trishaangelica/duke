import java.util.Scanner;
public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|";
        String line = "\n_______________________________________________\n";
        String input;
        System.out.println("Hello from\n" + logo);
        System.out.print(line + "Hello! I'm Duke\n" + "What can I do for you?" + line);
        Scanner scan = new Scanner(System.in);
        while(true) {
            input = scan.nextLine();
            if (input.equals("bye")) {
                break;
            }
            System.out.println(line + input + line);




        }
        System.out.print("Bye. Hope to see you again soon!");
        System.exit(0);
    }
}

