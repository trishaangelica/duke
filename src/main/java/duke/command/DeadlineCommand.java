package duke.command;

import duke.data.TaskList;
import duke.data.task.Deadlines;

/**
 * Adds a deadline-type task to the task list.
 */

public class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";
    public static final String MESSAGE_SUCCESS = "Got it. I've added this task: \n"
            +"\t%s.\nNow you have %d tasks in your list.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a simple deadline task to the list.\n"
            + "|| Parameters: deadline [DESCRIPTION] /by [DUEDATE]\n"
            + "|| Example: deadline submit essay /by Tues 4pm\n";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the list.";


    private Deadlines toAdd;

    public DeadlineCommand(Deadlines toAdd){
        this.toAdd = toAdd;
    }
    public DeadlineCommand(String desc,String dudate) throws StringIndexOutOfBoundsException {
        if (desc.isEmpty() || dudate.isEmpty()) {
            throw new StringIndexOutOfBoundsException();
        }
        this.toAdd = new Deadlines(desc,dudate);
    }

    @Override
    public CommandResult execute() {
        try {
            TaskList.add(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString(),TaskList.size()));
        } catch (TaskList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
}
