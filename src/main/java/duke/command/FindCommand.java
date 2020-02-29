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
    public static final String EMPTY_LIST_MESSAGE = "|| OOPS! There is no such task in the list.";
    public static final String MESSAGE_PARAM = TextUi.LS + "|| Parameters: " + COMMAND_WORD + " [KEYWORD]" + TextUi.LS;
    public static final String MESSAGE_EXAMPLE = "|| Example: " + COMMAND_WORD + " book" + TextUi.LS;
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
            System.out.println(TextUi.LS + TextUi.DIVIDER + TextUi.LS +"Here are the matching tasks in your list:"
                    + TextUi.LS);
            TaskList.showTaskList(filteredList);
            return new CommandResult(String.format(getMessageForTaskListShownSummary(filteredList),filteredList));
        } catch (NullPointerException e) {
            return new CommandResult(String.format(EMPTY_LIST_MESSAGE) + MESSAGE_PARAM + MESSAGE_EXAMPLE);
        }

    }

}
