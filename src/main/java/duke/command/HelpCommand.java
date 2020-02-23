package duke.command;

/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "|| Parameters: " + COMMAND_WORD
            + "\n"
            + "|| Example: " + COMMAND_WORD
            + "\n";

    @Override
    public CommandResult execute() {
        return new CommandResult(
                TodoCommand.MESSAGE_USAGE
                        + "\n" + EventCommand.MESSAGE_USAGE
                        + "\n" + DeadlineCommand.MESSAGE_USAGE
                        + "\n" + DoneCommand.MESSAGE_USAGE
                        + "\n" + DeleteCommand.MESSAGE_USAGE
                        + "\n" + FindCommand.MESSAGE_USAGE
                        + "\n" + ListCommand.MESSAGE_USAGE
                        + "\n" + ClearCommand.MESSAGE_USAGE
                        + "\n" + HelpCommand.MESSAGE_USAGE
                        + "\n" + ExitCommand.MESSAGE_USAGE
        );
    }
}