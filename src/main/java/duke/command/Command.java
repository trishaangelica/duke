package duke.command;

import duke.common.Messages;
import duke.data.TaskList;
import duke.data.task.Task;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents an executable command.
 */

public class Command {

    protected TaskList taskList;

    public static String getMessageForTaskListShownSummary(ArrayList<Task> tasksDisplayed) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, tasksDisplayed.size());
    }
    public CommandResult execute() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is to be implemented by child classes");
    };

    public void setData(TaskList taskList) {
        this.taskList = taskList;
    }




}