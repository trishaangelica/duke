package duke.command;

import duke.data.TaskList;
import duke.data.task.ToDos;

import static duke.common.Messages.MESSAGE_ERROR;


/**
 * Adds a todo-type of task in the task list.
 */
public class TodoCommand extends Command {
    public static final String COMMAND_WORD = "todo";
    public static final String MESSAGE_SUCCESS = "Got it. I've added this task: \n"
            + "\t%s.\nNow you have %d tasks in your list.";
    public static final String MESSAGE_PARAM = "\n|| Parameters: todo [DESCRIPTION]\n";
    public static final String MESSAGE_EXAMPLE ="|| Example: todo brainstorm ideas\n";
    public static final String MESSAGE_USAGE = "\n" + duke.ui.TextUi.DIVIDER + COMMAND_WORD
            + ": Adds a simple to-do task to the list."
            + MESSAGE_PARAM + MESSAGE_EXAMPLE;

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the list.";


    private ToDos toAdd;

    public TodoCommand(ToDos toAdd) {
        this.toAdd = toAdd;
    }

    public TodoCommand(String desc) throws NullPointerException {
        if (desc.isEmpty()) {
            throw new NullPointerException();
        }
        this.toAdd = new ToDos(desc);
    }

    @Override
    public CommandResult execute() {
        try {
            TaskList.add(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString(), TaskList.size()));
        } catch (TaskList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (NullPointerException NPE) {
            return new CommandResult(String.format(MESSAGE_ERROR, "description", COMMAND_WORD));
        }
    }
}
