package duke.command;
import duke.data.TaskList;
import duke.ui.TextUi;

/**
 * Clears the Task List.
 */

public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD +": Clears task list permanently.\n"
            + "|| Parameter: " + COMMAND_WORD
            + "\n"
            + "|| Example: " + COMMAND_WORD + "\n";

    public static final String MESSAGE_SUCCESS = "Task list has been cleared!";

    public ClearCommand() {
    }

    @Override
    public CommandResult execute() {
        TaskList.clear();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}