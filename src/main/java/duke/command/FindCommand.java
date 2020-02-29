package duke.command;

import duke.data.TaskList;
import duke.data.task.Task;
import duke.ui.TextUi;

import java.util.ArrayList;

import static duke.data.TaskList.filterList;


/**
 * Displays all the tasks in task list that matches the keyword.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    public static final String EMPTY_LIST_MESSAGE = "|| OOPS! There is no such task in your list.";
    public static final String MESSAGE_PARAM = "\n|| Parameters: " + COMMAND_WORD + " [KEYWORD]\n";
    public static final String MESSAGE_EXAMPLE = "|| Example: " + COMMAND_WORD + " book\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Find tasks stored in the list by keyword."
            + MESSAGE_PARAM + MESSAGE_EXAMPLE;
    String keyword;

    public FindCommand(String kw) {
        this.keyword = kw;
    }


    @Override
    public CommandResult execute() {
        try {
            ArrayList<Task> copiedList = TaskList.copy();
            ArrayList<Task> filteredList = filterList(copiedList, keyword);
            System.out.println("\n" + TextUi.DIVIDER + "\nHere are the matching tasks in your list:\n");
            TaskList.showTaskList(filteredList);
            return new CommandResult(String.format(getMessageForTaskListShownSummary(filteredList),filteredList));
        } catch (NullPointerException e) {
            return new CommandResult(String.format(EMPTY_LIST_MESSAGE) + MESSAGE_PARAM + MESSAGE_EXAMPLE);
        }

    }

}
