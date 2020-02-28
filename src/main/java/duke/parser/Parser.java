package duke.parser;

import duke.command.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
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

        case FindCommand.COMMAND_WORD:
            return new FindCommand(arguments);

        case FilterCommand.COMMAND_WORD:
            return prepareFilter(arguments);

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
                    + String.format(MESSAGE_ERROR, "description", TodoCommand.COMMAND_WORD)
                    + TodoCommand.MESSAGE_PARAM + TodoCommand.MESSAGE_EXAMPLE);
        }
    }

    private Command prepareEvent(String arguments) {
        try {
            final int indexOfAtPrefix = arguments.indexOf("/at");
            String description = arguments.substring(0, indexOfAtPrefix);
            String timeOfEvent = arguments.substring(indexOfAtPrefix + 3).trim();
            try {
                DateTimeFormatter oldPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                LocalDateTime datetime = LocalDateTime.parse(timeOfEvent, oldPattern);
                return new EventCommand(description, datetime); //Example: 0ct 15 2019 06:20 PM
            } catch (DateTimeParseException dpe) {
                return new EventCommand(description, timeOfEvent); //store date as string as it is not in parsable format
            }
        }catch (StringIndexOutOfBoundsException iob) {
            return new IncorrectCommand(System.lineSeparator()
                    + String.format(MESSAGE_ERROR, "description and/or time", EventCommand.COMMAND_WORD)
                    + EventCommand.MESSAGE_PARAM + EventCommand.MESSAGE_EXAMPLE);
        }
    }

    private Command prepareDeadline(String arguments) {
        try {
            final int indexOfByPrefix = arguments.trim().indexOf("/by");
            String description = arguments.trim().substring(0, indexOfByPrefix);
            String dueDate = arguments.substring(indexOfByPrefix + 3).trim();
            try {
                DateTimeFormatter oldPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                LocalDateTime datetime = LocalDateTime.parse(dueDate, oldPattern);
                return new DeadlineCommand(description, datetime); //Example: 0ct 15 2019 06:20 PM
            } catch (DateTimeParseException dpe) {
                return new DeadlineCommand(description,dueDate); //store date as string as it is not in parsable format
            }
            } catch (StringIndexOutOfBoundsException iob) {
                return new IncorrectCommand(System.lineSeparator()
                        + String.format(MESSAGE_ERROR, "description and/or due date", DeadlineCommand.COMMAND_WORD)
                        + DeadlineCommand.MESSAGE_PARAM + DeadlineCommand.MESSAGE_EXAMPLE);
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
            return new DoneCommand(targetIndex-1);
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    private int parseArgsAsDisplayedIndex(String args) throws NumberFormatException {
        return Integer.parseInt(args.trim());
    }

    private Command prepareFilter(String arguments) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
        try {
            Date date = formatter.parse(arguments);
            //System.out.println(formatter.format(date));
            return new FilterCommand(date); //Example: 0ct 15

        } catch (ParseException e) {
            return new FilterCommand(arguments); //filter keyword as string
        }
    }
}

