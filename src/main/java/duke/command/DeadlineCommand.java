package duke.command;

import duke.data.TaskList;
import duke.data.task.Deadlines;
import duke.ui.TextUi;
import java.time.LocalDateTime;

/**
 * Adds a deadline-type task to the task list.
 */

public class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";
    public static final String MESSAGE_SUCCESS = TextUi.LS + "Got it. I've added this task: " +TextUi.LS
            + "\t%s."+ TextUi.LS + "Now you have %d tasks in your list.";
    public static final String MESSAGE_PARAM = TextUi.LS + "|| Parameters: deadline [DESCRIPTION] /by [DUEDATE]"
            + TextUi.LS;
    public static final String MESSAGE_EXAMPLE ="|| Example: deadline submit essay /by 2019-10-15 1600" + TextUi.LS;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a simple deadline task to the list."
            + MESSAGE_PARAM + MESSAGE_EXAMPLE;
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the list.";

    private Deadlines toAdd;

    public DeadlineCommand(Deadlines toAdd){
        this.toAdd = toAdd;
    }

    public DeadlineCommand(String desc,String duedate) throws StringIndexOutOfBoundsException {
        if (desc.isEmpty() || duedate.isEmpty()) {
            throw new StringIndexOutOfBoundsException();
        }
        this.toAdd = new Deadlines(desc,duedate);
    }

    public DeadlineCommand(String desc, LocalDateTime date) throws StringIndexOutOfBoundsException {
        if (desc.isEmpty()||date.toString().isEmpty()){
            throw new StringIndexOutOfBoundsException();
        }
        this.toAdd = new Deadlines(desc.trim(),date);
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
