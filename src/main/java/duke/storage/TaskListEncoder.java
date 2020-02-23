package duke.storage;

import duke.data.TaskList;
import duke.data.task.Deadlines;
import duke.data.task.Events;
import duke.data.task.Task;
import duke.data.task.ToDos;

import java.util.ArrayList;
import java.util.List;

/**
 * Encodes the {@code AddressBook} object into a data file for storage.
 */
public class TaskListEncoder {
    public static List<String> encodeTaskList(TaskList toSave) {
        final List<String> encodedTask = new ArrayList<>();
        toSave.getAllTasks().forEach(task -> encodedTask.add(encodeTaskToString(task)));
        return encodedTask;
    }
    /**
     * Encodes the {@code person} into a decodable and readable string representation.
     */
    private static String encodeTaskToString(Task t) {
        String fileDoneStatus;
        if (t.getStatusIcon().equals("done")) {
            fileDoneStatus = "1";
        } else {
            fileDoneStatus = "0";
        }
        final StringBuilder encodedTaskBuilder = new StringBuilder();
        if (t instanceof ToDos){
            encodedTaskBuilder.append(t.getTaskType());
            encodedTaskBuilder.append(" | ").append(fileDoneStatus);
            encodedTaskBuilder.append(" | ").append(t.description);
        }
        else if (t instanceof Events) {
            encodedTaskBuilder.append(t.getTaskType());
            encodedTaskBuilder.append(" | ").append(fileDoneStatus);
            encodedTaskBuilder.append(" | ").append(t.description).append(" | ");
            encodedTaskBuilder.append(((Events) t).getTimeOfEvent());
        }
        else if (t instanceof Deadlines) {
            encodedTaskBuilder.append(t.getTaskType());
            encodedTaskBuilder.append(" | ").append(fileDoneStatus);
            encodedTaskBuilder.append(" | ").append(t.description).append(" | ");
            encodedTaskBuilder.append(((Deadlines) t).getDueDate());
        }

        return encodedTaskBuilder.toString();
    }
}
