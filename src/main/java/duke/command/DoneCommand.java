package duke.command;
import duke.common.Messages;
import duke.data.TaskList;
import duke.data.task.Task;


/**
 * Marks a task as [done] in the task list.
 */
public class DoneCommand extends Command{
    public static final String COMMAND_WORD = "done";
    int targetIndex;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a task (identified by index number) as done in the list.\n"
            + "|| Parameters: done [TASK NUMBER]\n"
            +"|| Example: done 1\n";

    public static final String MESSAGE_MARKED_TASK_DONE_SUCCESS = "Hooray! This task has been marked done: %1$s";

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {
        try {

            Task t = TaskList.markAsDone(targetIndex);
            return new CommandResult(String.format(MESSAGE_MARKED_TASK_DONE_SUCCESS,t.toString()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (TaskList.TaskNotFoundException tnf) {
            return new CommandResult(Messages.MESSAGE_TASK_NOT_IN_LIST);

        }
    }
}
