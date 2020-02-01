import java.util.*;

public class Duke {
  private static final String COMMAND_TODO = "todo";
  private static final String COMMAND_DEADLINE = "deadline";
  private static final String COMMAND_EVENT = "event";
  private static final String COMMAND_LIST = "list";
  private static final String COMMAND_DONE = "done";
  private static final String COMMAND_BYE = "bye";

  public static void main(String[] args) {
    String logo =
        " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|";
    String line = "\n - - - - - - - - - - - - - - - - - - - - - - - -  \n";
    String line2 = System.lineSeparator() + "|    ";
    String description;
    ArrayList<Task> taskList = new ArrayList<Task>();
    System.out.println("\nHello from\n" + logo);

    System.out.println(
        String.format(
            line + "|             %-34s|\n|       %-40s|",
            "Hello! I'm Duke :D", "What can I do for you today?") + line);

   // System.out.print(line2 + line + "Hello! I'm Duke :D" + line + "What can I do for you today?" + "\n" + line2);
    Scanner scan = new Scanner(System.in);

    while (true) {

      String input = scan.nextLine();
      final Command command = splitCommanandArgs(input);

      switch (command.getCommandName()) {
        case COMMAND_BYE: // exit program
          System.out.print(line + "Bye. Hope to see you again soon!" + line);
          System.exit(0);
          break;

        case COMMAND_LIST: // list tasks
          System.out.print(line);
          printStoredTasks(taskList);
          System.out.print(line);
          break;

        case COMMAND_DONE: // mark as done
          int index = Integer.parseInt(String.valueOf(command.getArgs()));
          Task t = retrieveTask(taskList, index - 1);
          t.markAsDone();
          updateTask(t, taskList, index - 1);
          markDoneMessage(line, t);
          break;

        case COMMAND_TODO: // add to-do
          Task newTask = new ToDos(command.getArgs());
          taskList.add(newTask);
          printSuccessfulAddition(line, taskList, newTask);
          break;

        case COMMAND_DEADLINE: // add deadlines
          final int indexOfByPrefix = command.getArgs().indexOf("/by");
          description = command.getArgs().substring(0, indexOfByPrefix);
          String dueDate =
              command.getArgs().substring(indexOfByPrefix + 3, command.getArgs().length()).trim();
          Task newDeadline = addNewDeadline(taskList, description, dueDate);
          printSuccessfulAddition(line, taskList, newDeadline);
          break;

        case COMMAND_EVENT: //add event
          final int indexOfAtPrefix = command.getArgs().indexOf("/at");
          description = command.getArgs().substring(0, indexOfAtPrefix);
          String timeOfEvent =
              command.getArgs().substring(indexOfAtPrefix + 3, command.getArgs().length()).trim();
          Task newEvent = addNewEvent(taskList, description, timeOfEvent);
          printSuccessfulAddition(line, taskList, newEvent);
          break;

        default: // handle invalid input
          System.out.println(line + "Sorry, you have entered an invalid input. Please try again.");
          break;
      }
    }
  }

  private static Command splitCommanandArgs(String input) {
    String com, args;
    try {
      com = input.substring(0, input.indexOf(' '));
      args = input.substring(input.indexOf(' ') + 1);
    } catch (IndexOutOfBoundsException e) {
      com = input; // input is a single word like "bye" or "list"
      args = " ";
    }
    Command command = new Command(com, args);
    return command;
  }

  private static void markDoneMessage(String line, Task t) {
    System.out.print(
        line
            + " Nice! I've marked this task as done: ["
            + t.getStatusIcon()
            + "] "
            + t.description
            + line);
  }

  private static Task addNewDeadline(ArrayList<Task> taskList, String description, String dueDate) {
    Task deadline = new Deadlines(description, dueDate);
    taskList.add(deadline);
    return retrieveTask(taskList, taskList.size() - 1);
  }

  private static Task addNewEvent(
      ArrayList<Task> taskList, String description, String timeOfEvent) {
    Task event = new Events(description, timeOfEvent);
    taskList.add(event);
    return retrieveTask(taskList, taskList.size() - 1);
  }

  private static void printSuccessfulAddition(String line, ArrayList<Task> taskList, Task newTask) {
    System.out.println(line + "Got it. I've added this task: ");
    System.out.print("\t" + newTask.toString());
    System.out.printf("\nNow you have %d tasks in your list." + line, taskList.size());
  }

  private static void printStoredTasks(ArrayList<Task> taskList) {

    int taskCounter = 1;
    System.lineSeparator();
    System.out.println("Currently, you have these items in your list: \n");
    for (Task t : taskList) {
      System.out.println(taskCounter + ". " + t.toString());
      taskCounter++;
    }
  }

  private static void updateTask(Task task, ArrayList<Task> taskArrayList, int i) {
    taskArrayList.set(i, task);
  }

  private static Task retrieveTask(ArrayList<Task> taskArrayList, int i) {

    Task t = taskArrayList.get(i);
    return t;
  }
}
