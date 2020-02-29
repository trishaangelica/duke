package duke.data;

import duke.common.Utils;
import duke.data.exception.DuplicateDataException;
import duke.data.task.Deadlines;
import duke.data.task.Events;
import duke.data.task.Task;
import duke.data.task.ToDos;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Represents the entire task  list. Contains the data of the task list.
 */
public class TaskList {

    public static ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Constructs empty task list.
     */
    public TaskList() {
    }

    /**
     * Constructs a list from the items in the given collection.
     *
     * @param Tasks a collection of tasks
     * @throws DuplicateTaskException if the {@code persons} contains duplicate tasks
     */
    public TaskList(List<Task> Tasks) throws DuplicateTaskException {
        if (!Utils.elementsAreUnique(Tasks)) {
            throw new DuplicateTaskException();
        }
        taskList.addAll(Tasks);
    }


    public static boolean contains(Task toCheck) {
        for (Task p : taskList) {
            if (p instanceof ToDos && toCheck instanceof ToDos) {
                return p.isSameTask(toCheck);
            } else if (p instanceof Events && toCheck instanceof Events) {
                return (p.isSameTask(toCheck));
            } else if (p instanceof Deadlines && toCheck instanceof Deadlines) {
                return p.isSameTask(toCheck);
            }
        }
        return false;
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public static void add(Task toAdd) throws DuplicateTaskException {
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }else {
            taskList.add(toAdd);
        }
    }

    public static Task retrieve(int targetIndex) {
        return taskList.get(targetIndex);
    }

    /**
     * Removes the specified task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public static void remove(int toRemove) throws TaskNotFoundException {
        Task t = taskList.get(toRemove);
        final boolean taskFoundAndDeleted = taskList.remove(t);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Gets the number of tasks currently in the list.
     */
    public static int size() {
        return taskList.size();
    }

    /**
     * Clears task list.
     */
    public static void clear() {
        taskList.clear();
    }

    /**
     * @return copy of task list
     */
    public static ArrayList<Task> copy() {
        // Clone the list
        return (new ArrayList<>(taskList));
    }

    //by word
    public static ArrayList<Task> filterList(ArrayList<Task> taskList, String keyword) {

        if(keyword.equals("T")||keyword.equals("E")||keyword.equals("D")){
            return (ArrayList<Task>) taskList.stream()
                    .filter(task -> task.taskType.equals(keyword))
                    .collect(Collectors.toList());
        }
        else if(keyword.matches(".*done.*")){
            return (ArrayList<Task>) taskList.stream()
                    .filter(task -> task.getStatus().equals(keyword))
                    .collect(Collectors.toList());
        }

        return (ArrayList<Task>) taskList.stream()
                .filter(task -> task.description.contains(keyword))
                .collect(Collectors.toList());
    }
    //by datetime object
    public static ArrayList<Task> filterList(ArrayList<Task> taskList, Date keyword) {
        ArrayList<Task> filteredArray = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
        DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("MMM dd");

        String toCompare = formatter.format(keyword);
        for (Task p : taskList) {
            if (p instanceof Events) {
                if (((Events) p).getEventTime()!= null) {
                    if (((Events) p).getEventTime().format(newPattern).equals(toCompare)) {
                        filteredArray.add(p);
                    }
                }
            }
            if (p instanceof Deadlines) {
                if(p.getDate()!= null) {
                    if (p.getDate().format(newPattern).equals(toCompare)) {
                        filteredArray.add(p);
                    }
                }
            }
        }
        Collections.sort(filteredArray);

        return filteredArray;
    }


        /**
         * Shows to user all elements in stored task list.
         */
    public static void showStoredTaskList() throws NullPointerException {
        int taskCounter = 1;
        if (taskList.size() == 0) {
            throw new NullPointerException();
        } else {
            for (Task t : taskList) {
                if(taskCounter>=10){ //for pretty alignment if there's >10 tasks
                    System.out.println("    \b"+ taskCounter + ". " + t.toString());
                }else {
                    System.out.println("\t" + taskCounter + ". " + t.toString());
                }
                taskCounter++;
            }
        }
    }

    /**
     * Shows to user all elements in specified task list.
     */
    public static void showTaskList(ArrayList<Task> taskList) throws NullPointerException {
        int taskCounter = 1;
        if (taskList.size() == 0) {
            throw new NullPointerException();
        } else {
            for (Task t : taskList) {
                System.out.println("\t" + taskCounter + ". " + t.toString());
                taskCounter++;
            }
        }
    }

    /**
     * Marks a task as [done].
     */
    public static Task markAsDone(int targetIndex) throws TaskNotFoundException {
        Task t = TaskList.retrieve(targetIndex);
        t.markAsDone();
        final boolean taskFoundandMarked = t.isDone;
        if (!taskFoundandMarked) {
            throw new TaskNotFoundException();
        }
        return t;
    }

    /**
     * Returns a new task list of all tasks in list at the time of the call.
     */
    public ArrayList<Task> getAllTasks() {
        return taskList;
    }


    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }


    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {
        protected TaskNotFoundException() {
            super("|| OOPS! I can't delete that because you haven't added that task yet!");
        }
    }


}
