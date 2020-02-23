package duke.command;


import duke.common.Messages;
import duke.data.TaskList;
import duke.data.task.Task;

/**
 * Deletes a task identified by it's index from the task list.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    int targetIndex;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task (identified by index number) in the list.\n"
            + "|| Parameters: delete [TASK NUMBER]\n"
            + "|| Example: delete 1"
            + "\n";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s.\n";

    public DeleteCommand(int targetIndex) {
       this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {
        try {
            Task t = TaskList.retrieve(targetIndex-1);
            TaskList.remove(targetIndex-1);
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS,t.toString())
                    + "\tNow you have " + taskList.size() + " tasks in your list.");

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(String.format(Messages.MESSAGE_TASK_NOT_IN_LIST,targetIndex));
        } catch (TaskList.TaskNotFoundException pnfe) {
            return new CommandResult(String.format(Messages.MESSAGE_TASK_NOT_IN_LIST,targetIndex));

        }
    }

}
