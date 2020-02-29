package duke.command;
import duke.data.TaskList;
import duke.data.task.Task;
import duke.ui.TextUi;

import java.util.ArrayList;


/**
 * Displays all the tasks currently in the task list.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String EMPTY_LIST_MESSAGE = "|| OOPS! List is currently empty.";
    public static final String MESSAGE_PARAM = "|| Parameters: " + COMMAND_WORD+ TextUi.LS;
    public static final String MESSAGE_EXAMPLE = "|| Example: " + COMMAND_WORD + TextUi.LS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all tasks stored in the list."
            + TextUi.LS
            + MESSAGE_PARAM + MESSAGE_EXAMPLE;

    @Override
    public CommandResult execute() {
        try {
            ArrayList<Task> copiedList = TaskList.copy();
            System.out.print(TextUi.DIVIDER);
            System.out.println(TextUi.LS + "Currently, you have these items in your list: " + TextUi.LS);
            TaskList.showTaskList(copiedList);
            return new CommandResult(String.format(getMessageForTaskListShownSummary(copiedList),copiedList));
        } catch (NullPointerException e) {
            return new CommandResult(String.format(EMPTY_LIST_MESSAGE));
        }

    }
}
