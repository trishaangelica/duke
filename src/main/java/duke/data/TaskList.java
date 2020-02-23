package duke.data;

import duke.common.Utils;
import duke.data.exception.DuplicateDataException;
import duke.data.task.Deadlines;
import duke.data.task.Events;
import duke.data.task.Task;
import duke.data.task.ToDos;

import java.util.ArrayList;
import java.util.List;


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
                return (((ToDos)p).isSameTask(toCheck));
            } else if (p instanceof Events && toCheck instanceof Events) {
                return (((Events)p).isSameTask(toCheck));
            } else if (p instanceof Deadlines && toCheck instanceof Deadlines) {
                return (((Deadlines)p).isSameTask(toCheck));
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
        }
        taskList.add(toAdd);
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

    /**
     * Shows to user all elements in task list.
     */
    public static void showTaskList() throws NullPointerException {
        int taskCounter = 1;
        if (taskList.size() == 0) {
            throw new NullPointerException();
        } else {
            System.out.println(System.lineSeparator() + "Currently, you have these items in your list: \n");
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
