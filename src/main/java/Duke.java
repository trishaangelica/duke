import java.util.*;

public class Duke {

    public static void printTaskList(ArrayList<Task> taskList){

        int counter = 1;
        System.lineSeparator();
        System.out.println("Currently, you have these items in your list: \n");
        for (Task t: taskList)
        {
             System.out.println(counter + ". " + "[" + t.getStatusIcon() + "] " + t.description);
             counter++;
        }

    }

    public static void updateTask(Task task, ArrayList<Task> taskArrayList,int i){
        taskArrayList.set(i,task);

    }

    public static void addNewTask(String description,ArrayList<Task> taskArrayList){

        Task t = new Task(description);
        taskArrayList.add(t);


    }

    public static Task retrieveTask(ArrayList<Task> taskArrayList, int i){

        Task t = taskArrayList.get(i);
       return t;

    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|";
        String line = "\n_______________________________________________\n";
        String input, rest;
        //String[] taskList = new String[100];
        ArrayList<Task> taskList = new ArrayList<Task>();
        System.out.println("Hello from\n" + logo);
        System.out.print(line + "Hello! I'm Duke\n" + "What can I do for you?" + line);
        Scanner scan = new Scanner(System.in);
        while (true) {

            input = scan.next();

            switch (input) {
                case "bye":
                    System.out.print(line + "Bye. Hope to see you again soon!" + line);
                    System.exit(0);
                    break;

                case "list":
                    System.out.print(line);
                    //taskList = Arrays.stream(taskList).filter(Objects::nonNull).toArray(String[]::new); //remove null values of array
                    printTaskList(taskList);
                    System.out.print(line);
                    break;

                case "done":
                    int index = scan.nextInt();
                    Task t = retrieveTask(taskList,index-1);
                    t.markAsDone();
                    updateTask(t,taskList,index-1);
                    System.out.print(line + " Nice! I've marked this task as done: [" + t.getStatusIcon() + "] " + t.description + line);
                    break;


                default:
                    rest = scan.nextLine();
                    input = input.concat(rest);
                    addNewTask(input, taskList);
                    Task newlyAdded = retrieveTask(taskList,taskList.size()-1);
                    System.out.print(line + "added: " + newlyAdded.description + line);
                    break;
            }

        }

    }
}

