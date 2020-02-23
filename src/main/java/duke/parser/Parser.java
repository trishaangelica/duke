package duke.parser;

import duke.command.Command;
import duke.command.TodoCommand;
import duke.command.EventCommand;
import duke.command.DeadlineCommand;
import duke.command.DoneCommand;
import duke.command.DeleteCommand;
import duke.command.ListCommand;
import duke.command.ClearCommand;
import duke.command.ExitCommand;
import duke.command.HelpCommand;
import duke.command.IncorrectCommand;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static duke.common.Messages.*;

public class Parser {
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */


    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandword>^[\\S]+)(?<arguments>[\\d\\s\\S]*$)");

    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandword").trim();
        final String arguments = matcher.group("arguments").trim();

        switch (commandWord) {
        case TodoCommand.COMMAND_WORD:
            return prepareTodo(arguments);

        case EventCommand.COMMAND_WORD:
            return prepareEvent(arguments);

        case DeadlineCommand.COMMAND_WORD:
            return prepareDeadline(arguments);

        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            return new HelpCommand();
        }

    }


    private Command prepareTodo(String arguments) {
        try {
            return new TodoCommand(arguments);
        } catch (NullPointerException npe) {
            return new IncorrectCommand(System.lineSeparator()
                    + String.format(MESSAGE_ERROR, "description", TodoCommand.COMMAND_WORD));
        }
    }

    private Command prepareEvent(String arguments) {
       try {
           final int indexOfAtPrefix = arguments.indexOf("/at");

           String description = arguments.substring(0, indexOfAtPrefix);
           String timeOfEvent = arguments.substring(indexOfAtPrefix + 3).trim();
           return new EventCommand(description, timeOfEvent);
       } catch (StringIndexOutOfBoundsException iob) {
           return new IncorrectCommand(System.lineSeparator()
                   + String.format(MESSAGE_ERROR, "description and/or time", EventCommand.COMMAND_WORD));
        }
    }

    private Command prepareDeadline(String arguments) {
        try {
            final int indexOfByPrefix = arguments.trim().indexOf("/by");
            String description = arguments.trim().substring(0, indexOfByPrefix);
            String dueDate = arguments.substring(indexOfByPrefix + 3).trim();
            return new DeadlineCommand(description, dueDate);
        } catch (StringIndexOutOfBoundsException iob) {
            return new IncorrectCommand(System.lineSeparator()
                    + String.format(MESSAGE_ERROR, "description and/or due date", DeadlineCommand.COMMAND_WORD));
        }
    }
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param arguments full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String arguments) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(arguments);
            return new DeleteCommand(targetIndex);
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    private Command prepareDone(String arguments) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(arguments);

            return new DoneCommand(targetIndex);
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    private int parseArgsAsDisplayedIndex(String args) throws NumberFormatException {
        return Integer.parseInt(args.trim());
    }

}


