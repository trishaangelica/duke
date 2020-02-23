package duke.storage;
import duke.data.TaskList;
import duke.data.task.Deadlines;
import duke.data.task.Events;
import duke.data.task.Task;
import duke.data.task.ToDos;
import java.util.*;


/**
 * Decodes the storage data file into an {@code AddressBook} object.
 */
public class TaskListDecoder {
    /**
     * Decodes {@code encodedAddressBook} into an {@code AddressBook} containing the decoded persons.
     *
     */
    public static TaskList decodeTaskList(List<String> encodedTaskList) throws TaskList.DuplicateTaskException {
        final List<Task> decodedTasks = new ArrayList<>();
        for (String encodedTask : encodedTaskList) {
            decodedTasks.add(decodeTaskFromString(encodedTask));
    }
        return new TaskList(decodedTasks);
    }

    /**
     * Decodes {@code encodedTask} into a {@code Task}.
     *
     */
    private static Task decodeTaskFromString(String encodedTask) {
        String[] parse = encodedTask.split("\\s*\\|\\s*");
        if (parse.length == 3) {
            ToDos t = new ToDos(parse[2]);
            if (parse[1].equals("1")) {
                t.markAsDone();
            }
            return t;
        } else if (parse.length == 4) {
            if (parse[0].equals("D")) {
                Deadlines d = new Deadlines(parse[2], parse[3]);
                if (parse[1].equals("1")) {
                    d.markAsDone();
                }
                return d;
            } else {
                Events e = new Events(parse[2], parse[3]);
                if (parse[1].equals("1")) {
                    e.markAsDone();
                }
                return e;
            }
        }
        return null;
    }
}
