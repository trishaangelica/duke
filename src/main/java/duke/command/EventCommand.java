package duke.command;
import duke.data.TaskList;
import duke.data.task.Events;
import duke.ui.TextUi;

import java.time.LocalDateTime;


/**
 * Adds an event-type of task in the task list.
 */
public class EventCommand extends Command {
    public static final String COMMAND_WORD = "event";
    public static final String MESSAGE_SUCCESS = TextUi.LS + "Got it. I've added this task: " + TextUi.LS
            +"\t%s." + TextUi.LS + "Now you have %d tasks in your list.";
    public static final String MESSAGE_PARAM = TextUi.LS + "|| Parameters: event [DESCRIPTION] /at [TIME]" + TextUi.LS;
    public static final String MESSAGE_EXAMPLE ="|| Example: event project meeting /at Mon 4pm" + TextUi.LS;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event task to the list."
            + MESSAGE_PARAM + MESSAGE_EXAMPLE;
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the list.";

    private Events toAdd;

    public EventCommand(Events toAdd) {
        this.toAdd = toAdd;
    }

    public EventCommand(String desc, String date) throws StringIndexOutOfBoundsException {
        if(desc.isEmpty() || date.isEmpty()){
            throw new StringIndexOutOfBoundsException();
        }
        this.toAdd = new Events(desc, date);
    }

    public EventCommand(String desc, LocalDateTime date) throws StringIndexOutOfBoundsException {
        if (desc.isEmpty()||date.toString().isEmpty()){
            throw new StringIndexOutOfBoundsException();
        }
        this.toAdd = new Events(desc,date);
    }

    @Override
    public CommandResult execute() {
        try {
            TaskList.add(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, TaskList.size()));
        } catch (TaskList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
}