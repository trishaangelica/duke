import duke.command.Command;
import duke.exception.DukeException;
import duke.task.Deadlines;
import duke.task.Events;
import duke.task.Task;
import duke.task.ToDos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {

    private static final Path FILENAME = Paths
            .get(System.getProperty("user.dir"), "data", "duke.txt");
    private static String fileDoneStatus;
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_TODO_DESC = "Add a simple to-do task.";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_DEADLINE_DESC = "Add a deadline.";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_EVENT_DESC = "Add an event.";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_LIST_DESC = "List all tasks at hand.";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_DELETE_DESC = "Delete specific task from list.";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_DONE_DESC = "Mark specific task as done.";
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_BYE_DESC = "Exit program.";
    private static final String COMMAND_MESSAGE = "|| %s: %s\n||  Example: %s\n||\n";
    private static final String ERROR_MESSAGE = "||\t\u2639 OOPS! The %s of a %s command cannot be empty.\n";
    private static final String ERROR_TODO_PARAM = "|| Parameters: todo [DESCRIPTION]\n";
    private static final String ERROR_TODO_EXAMPLE = "|| Example: todo brainstorm ideas";
    private static final String ERROR_DEADLINES_PARAM = "|| Parameters: deadline [DESCRIPTION] /by [DUEDATE]\n";
    private static final String ERROR_DEADLINES_EXAMPLE = "|| Example: deadline submit essay /by Tues 4pm";
    private static final String ERROR_EVENTS_PARAM = "|| Parameters: event [DESCRIPTION] /at [TIME]\n";
    private static final String ERROR_EVENTS_EXAMPLE = "|| Example: event project meeting /at Mon 4pm";
    private static final String ERROR_DELETE_PARAM = "|| Parameters: delete [TASK NUMBER]\n";
    private static final String ERROR_DELETE_EXAMPLE = "|| Example: delete 1";
    private static final String ERROR_DONE_PARAM = "|| Parameters: done [TASK NUMBER]\n";
    private static final String ERROR_DONE_EXAMPLE = "|| Example: done 1";
    private static final String ERROR_LIST_MESSAGE = "||\t\u2639 OOPS! That task cannot be marked done.\n|| Try adding a task first. ";
    private static final String ERROR_DELETE_MESSAGE = "||\t\u2639 OOPS! I can't delete that because you haven't added task %s yet!";
    private static final String divider = "\n - - - - - - - - - - - - - - - - - - - - - - - - \n";

    public static void main(String[] args) {
        String logo = " _ _ __                           \n"
                + "|_ _   |  _ _     _   __   _      _   _ _  _ _ _\n"
                + "    |  | / _ \\   | |/ __|[   ]  [   ]|_=_||      |\n"
                + " _  |  || | | \\  |  /     \\ \\   / /    _   \\  \\ /\n"
                + "| |_|  || |_|  \\ |  |      \\ \\_/ /    | | | \\  \\\n"
                + "|_ _ _ / \\_ _/\\_\\[_ _]      \\__ /     |_| |_ _ _/";
        ArrayList<Task> taskList = new ArrayList<>();
        System.out.println("\nHello from\n" + logo + ",");
        System.out.println(String.format(divider + "|             %-34s|\n|       %-40s|",
                "Hello! I'm Jarvis :D", "What can I do for you today?") + divider);
        String input;
        Scanner scan = new Scanner(System.in);
        System.out.println(divider + "Loading previously saved tasks (if any)..\n");
        try {
            readIOFile(taskList);
            printStoredTasks(taskList);
            System.out.println("\nGreat all loaded. Let's begin to add more tasks!\n" + divider);

        } catch (IOException | DukeException e) {
            System.out.println(
                    "Well this is embarrassing, you haven't got any saved list.\nLet's create a new one!\n"
                            + divider);
        }

        while (true) {
            System.out.println("Enter command: ");
            input = scan.nextLine();
            final Command command = splitCommandAndArgs(input);

            switch (command.getCommandName()) {
            case COMMAND_BYE: // exit program
                System.out.println("Hang on, saving your list...");
                outWriteTaskList(taskList);
                System.out
                        .print(divider + "\uD83D\uDC4B Bye. Hope to see you again soon!" + divider);
                System.exit(0);
                break;

            case COMMAND_LIST: // list tasks
                System.out.print(divider);
                try {
                    printStoredTasks(taskList);
                } catch (DukeException e) {
                    System.out.println(System.lineSeparator()
                            + "Well, this is awkward. You don't have any task saved yet...");
                }
                System.out.print(divider);
                break;

            case COMMAND_DELETE:
                try {
                    deleteTask(taskList, command);
                } catch (NumberFormatException e) {
                    System.out.println(
                            String.format(divider + ERROR_MESSAGE, "task number", COMMAND_DELETE)
                                    + ERROR_DELETE_PARAM + ERROR_DELETE_EXAMPLE + divider);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(
                            divider + String.format(ERROR_DELETE_MESSAGE, command.getArgs())
                                    + divider);
                }
                break;

            case COMMAND_DONE: // mark as done
                try {
                    markTaskDone(taskList, command);
                } catch (NumberFormatException e) {
                    System.out.println(
                            String.format(divider + ERROR_MESSAGE, "task number", COMMAND_DONE)
                                    + ERROR_DONE_PARAM + ERROR_DONE_EXAMPLE + divider);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(divider + ERROR_LIST_MESSAGE + divider);
                }
                break;

            case COMMAND_TODO: // add to-do
                try {
                    addNewToDo(taskList, command);
                    outWriteTaskList(taskList);
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println(
                            String.format(divider + ERROR_MESSAGE, "description", COMMAND_TODO)
                                    + ERROR_TODO_PARAM + ERROR_TODO_EXAMPLE + divider);
                }
                break;

            case COMMAND_DEADLINE: // add deadlines
                try {
                    addNewDeadline(taskList, command);
                    outWriteTaskList(taskList);
                } catch (NullPointerException | StringIndexOutOfBoundsException e) {
                    System.out.println(
                            String.format(divider + ERROR_MESSAGE, "description and/or due date",
                                    COMMAND_DEADLINE) + ERROR_DEADLINES_PARAM
                                    + ERROR_DEADLINES_EXAMPLE + divider);
                }
                break;

            case COMMAND_EVENT: // add event
                try {
                    addNewEvent(taskList, command);
                    outWriteTaskList(taskList);
                } catch (NullPointerException | StringIndexOutOfBoundsException e) {
                    System.out.println(
                            String.format(divider + ERROR_MESSAGE, "description and/or time",
                                    COMMAND_EVENT) + ERROR_EVENTS_PARAM + ERROR_EVENTS_EXAMPLE
                                    + divider);
                }
                break;

            default: // Invalid input; List accepted commands
                System.out.println(divider
                        + "\uD83D\uDEC8 Sorry, you have entered an invalid input. \uD83D\uDEC8\n\t\tHere's a help list to get you started: \n");
                getCommandInfo();
                break;
            }
        }

    }

    private static void deleteTask(ArrayList<Task> taskList, Command command) {
        int index = Integer.parseInt(String.valueOf(command.getArgs()));
        Task t = retrieveTask(taskList, index - 1);
        taskList.remove(index - 1);
        deleteOnIOFile(index);
        System.out.print(Duke.divider + " Noted! I've removed this task:" + System.lineSeparator()
                + "\t" + t.toString() + System.lineSeparator() + String
                .format(" Now you have %d tasks in your list.", taskList.size()) + Duke.divider);

    }

    private static void markTaskDone(ArrayList<Task> taskList, Command command) {
        int index = Integer.parseInt(String.valueOf(command.getArgs()));
        Task t = retrieveTask(taskList, index - 1);
        t.markAsDone();
        updateTask(t, taskList, index - 1);
        updateIOFile(index, t);
        System.out
                .print(Duke.divider + " Nice! I've marked this task as done: [" + t.getStatusIcon()
                        + "] " + t.description + Duke.divider);
    }

    private static void getCommandInfo() {
        System.out.println(
                String.format(COMMAND_MESSAGE, COMMAND_TODO, COMMAND_TODO_DESC, COMMAND_TODO)
                        + String.format(COMMAND_MESSAGE, COMMAND_DEADLINE, COMMAND_DEADLINE_DESC,
                        COMMAND_DEADLINE) + String
                        .format(COMMAND_MESSAGE, COMMAND_EVENT, COMMAND_EVENT_DESC, COMMAND_EVENT)
                        + String
                        .format(COMMAND_MESSAGE, COMMAND_LIST, COMMAND_LIST_DESC, COMMAND_LIST)
                        + String
                        .format(COMMAND_MESSAGE, COMMAND_DONE, COMMAND_DONE_DESC, COMMAND_DONE)
                        + String.format(COMMAND_MESSAGE, COMMAND_DELETE, COMMAND_DELETE_DESC,
                        COMMAND_DELETE) + String
                        .format(COMMAND_MESSAGE, COMMAND_BYE, COMMAND_BYE_DESC, COMMAND_BYE)
                        + divider);
    }

    private static void addNewEvent(ArrayList<Task> taskList, Command command)
            throws StringIndexOutOfBoundsException {
        String description;
        final int indexOfAtPrefix = command.getArgs().indexOf("/at");
        description = command.getArgs().substring(0, indexOfAtPrefix);
        String timeOfEvent = command.getArgs().substring(indexOfAtPrefix + 3).trim();
        if (description.isEmpty() || timeOfEvent.isEmpty()) {
            throw new StringIndexOutOfBoundsException();
        }
        Task event = new Events(description, timeOfEvent);
        taskList.add(event);
        printSuccessfulAddition(taskList, event);
    }

    private static void addNewDeadline(ArrayList<Task> taskList, Command command)
            throws StringIndexOutOfBoundsException {
        String description;
        final int indexOfByPrefix = command.getArgs().trim().indexOf("/by");
        description = command.getArgs().trim().substring(0, indexOfByPrefix);
        String dueDate = command.getArgs().substring(indexOfByPrefix + 3).trim();
        if (description.isEmpty() || dueDate.isEmpty()) {
            throw new StringIndexOutOfBoundsException();
        }
        Task deadline = new Deadlines(description, dueDate);
        taskList.add(deadline);
        printSuccessfulAddition(taskList, deadline);
    }

    private static void addNewToDo(ArrayList<Task> taskList, Command command)
            throws StringIndexOutOfBoundsException {
        if (command.getArgs() == null) {
            throw new StringIndexOutOfBoundsException();
        } else {
            Task newTask = new ToDos(command.getArgs());
            taskList.add(newTask);
            printSuccessfulAddition(taskList, newTask);
        }
    }

    private static Command splitCommandAndArgs(String input) {
        String com, args;
        try {
            com = input.trim().substring(0, input.indexOf(' '));
            if (com.equals("done") || com.equals("delete")) {
                args = input.trim().substring(input.indexOf(' ')).trim();
            } else {
                args = input.substring(input.indexOf(' ') + 1);
            }
        } catch (StringIndexOutOfBoundsException e) { // input is "bye" or "list"; set args: null
            com = input.trim();
            args = null;
        }
        return new Command(com, args);
    }

    private static void printSuccessfulAddition(ArrayList<Task> taskList, Task newTask) {
        System.out.println(Duke.divider + "Got it. I've added this task: ");
        System.out.print("\t" + newTask.toString());
        System.out.printf("\nNow you have %d tasks in your list." + Duke.divider, taskList.size());
    }

    private static void printStoredTasks(ArrayList<Task> taskList) throws DukeException {
        int taskCounter = 1;
        if (taskList.size() == 0) {
            throw new DukeException();
        } else {
            System.out.println(System.lineSeparator()
                    + "Currently, you have these items in your list: \uD83D\uDCCC\n");
            for (Task t : taskList) {
                System.out.println(taskCounter + ". " + t.toString());
                taskCounter++;
            }
        }
    }

    private static void updateTask(Task task, ArrayList<Task> taskArrayList, int i) {
        taskArrayList.set(i, task);
    }


    private static Task retrieveTask(ArrayList<Task> taskArrayList, int i) {
        return taskArrayList.get(i);
    }

    private static void readIOFile(ArrayList<Task> taskArrayList)
            throws IOException {
        FileReader fr = new FileReader(String.valueOf(FILENAME));
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parse = line.trim().split("\\s*\\|\\s*");
            if (parse.length == 3) {
                ToDos t = new ToDos(parse[2]);
                if (parse[1].equals("1")) {
                    t.markAsDone();
                }
                taskArrayList.add(t);
            } else if (parse.length == 4) {
                if (parse[0].equals("D")) {
                    Deadlines d = new Deadlines(parse[2], parse[3]);
                    if (parse[1].equals("1")) {
                        d.markAsDone();
                    }
                    taskArrayList.add(d);
                } else {
                    Events e = new Events(parse[2], parse[3]);
                    if (parse[1].equals("1")) {
                        e.markAsDone();
                    }
                    taskArrayList.add(e);
                }
            }

        }
        br.close();
        fr.close();
    }

    //write to duke.txt
    private static void outWriteTaskList(ArrayList<Task> taskArrayList) {
        boolean directoryExists = Files.exists(FILENAME); // C:\cs2113T\duke\data\duke.txt
        try {
            if (directoryExists) {
                FileWriter fw = new FileWriter(String.valueOf(FILENAME));
                Writer output = new BufferedWriter(fw);
                for (Task t : taskArrayList) {
                    if (t.getStatusIcon().equals("\u2713")) {
                        fileDoneStatus = "1";
                    } else {
                        fileDoneStatus = "0";
                    }
                    if (t instanceof ToDos) {
                        output.write(
                                t.getTaskType() + " | " + fileDoneStatus + " | " + t.description
                                        + "\n");
                    } else if (t instanceof Events) {
                        output.write(
                                t.getTaskType() + " | " + fileDoneStatus + " | " + t.description
                                        + "|" + ((Events) t).getTimeOfEvent() + "\n");
                    } else if (t instanceof Deadlines) {
                        output.write(
                                t.getTaskType() + " | " + fileDoneStatus + " | " + t.description
                                        + "|" + ((Deadlines) t).getDueDate() + "\n");
                    }
                }
                output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //edit specific task in duke.txt
    public static void updateIOFile(int lineNumber, Task t) {
        if (t.getStatusIcon().equals("\u2713")) {
            fileDoneStatus = "1";
        } else {
            fileDoneStatus = "0";
        }
        try {
            List<String> lines = new ArrayList<>(
                    Files.readAllLines(FILENAME, StandardCharsets.UTF_8));

            if (t instanceof ToDos) {
                lines.set(lineNumber - 1,
                        t.getTaskType() + " | " + fileDoneStatus + " | " + t.description + "\n");
            } else if (t instanceof Events) {
                lines.set(lineNumber - 1,
                        t.getTaskType() + " | " + fileDoneStatus + " | " + t.description + "| "
                                + ((Events) t).getTimeOfEvent() + "\n");
            } else if (t instanceof Deadlines) {
                lines.set(lineNumber - 1,
                        t.getTaskType() + " | " + fileDoneStatus + " | " + t.description + "| "
                                + ((Deadlines) t).getDueDate() + "\n");
                Files.write(FILENAME, lines, StandardCharsets.UTF_8);
            }
            Files.write(FILENAME, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void deleteOnIOFile(int lineNumber) {
        try {
            List<String> lines = new ArrayList<>(
                    Files.readAllLines(FILENAME, StandardCharsets.UTF_8));
            lines.remove(lineNumber - 1);
            Files.write(FILENAME, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

