package duke.command;

import duke.data.TaskList;
import duke.data.task.Task;
import duke.ui.TextUi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Shows a filtered list based on keyword.
 * If keyword provided is in MMM-dd format, tasks in list will be in chronological order.
 * From earliest to latest time of task.
 */

import static duke.data.TaskList.filterList;

public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String EMPTY_LIST_MESSAGE = TextUi.LS + "|| OOPS! No task in the list fits that category..."
            + TextUi.LS + "|| Accepted category words are: [todo, event, deadline, done, undone, [MMM-dd]]"
            + TextUi.LS;
    public static final String MESSAGE_PARAM = "|| Parameters: " + COMMAND_WORD + " [CATEGORY]" + TextUi.LS;
    public static final String MESSAGE_EXAMPLE = "|| Example 1: " + COMMAND_WORD
            + " event" + TextUi.LS + "|| Example 2: " + COMMAND_WORD + " Mar 02";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays tasks that fits category." + TextUi.LS
            + MESSAGE_PARAM + MESSAGE_EXAMPLE + TextUi.LS;
    String filterParam;
    String keyword; //keep original keyword
    Date date;


    /**
     * @param keyword filters the list based on keyword provided
     */


    public FilterCommand(String keyword) {
        if (keyword.equals("todo")) {
            this.keyword = keyword;
            this.filterParam = "T";
        } else if (keyword.equals("event")) {
            this.keyword = keyword;
            this.filterParam = "E";
        } else if (keyword.equals("deadline")) {
            this.keyword = keyword;
            this.filterParam = "D";
        } else if (keyword.matches(".*done.*")) { //filter done/undone
            this.keyword = keyword;
            this.filterParam = keyword;
        }
    }

    /**
     * @param dateTime filters the list to show events occurring on specified date.
     */
    public FilterCommand(Date dateTime) {
        this.date = dateTime;
    }


    @Override
    public CommandResult execute() {
        ArrayList<Task> copiedList = TaskList.copy();
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM");
        try {
            ArrayList<Task> filteredList = filterList(copiedList, date);
            String showDate = formatter.format(date);
            System.out.println(TextUi.LS + TextUi.DIVIDER + TextUi.LS + "Here are the tasks you have on ["
                    + showDate + "]" + TextUi.LS);
            TaskList.showTaskList(filteredList);
            return new CommandResult(String.format(getMessageForTaskListShownSummary(filteredList), filteredList));
        } catch (NullPointerException e) { //exception for Date means it doesn't exist at this point, parse String instead
            try {
                ArrayList<Task> filteredList = filterList(copiedList, filterParam);
                System.out.println(TextUi.LS + TextUi.DIVIDER + TextUi.LS + "Here are the ["
                        + keyword + "] tasks in your list." + TextUi.LS);
                TaskList.showTaskList(filteredList);
                return new CommandResult(String.format(getMessageForTaskListShownSummary(filteredList), filteredList));
            } catch (NullPointerException npe) { //exception for string, it doesn't exist too so print error message
                return new CommandResult(String.format(EMPTY_LIST_MESSAGE)
                        + MESSAGE_PARAM + MESSAGE_EXAMPLE);

            }
        }
    }
}
