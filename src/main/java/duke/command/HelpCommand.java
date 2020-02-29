package duke.command;

import duke.ui.TextUi;

/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions." + TextUi.LS
            + "|| Parameters: " + COMMAND_WORD
            + TextUi.LS
            + "|| Example: " + COMMAND_WORD
            + TextUi.LS;

    @Override
    public CommandResult execute() {
        return new CommandResult(
                TodoCommand.MESSAGE_USAGE
                        + TextUi.LS + EventCommand.MESSAGE_USAGE
                        + TextUi.LS + DeadlineCommand.MESSAGE_USAGE
                        + TextUi.LS + DoneCommand.MESSAGE_USAGE
                        + TextUi.LS + DeleteCommand.MESSAGE_USAGE
                        + TextUi.LS + FindCommand.MESSAGE_USAGE
                        + TextUi.LS + FilterCommand.MESSAGE_USAGE
                        + TextUi.LS + ListCommand.MESSAGE_USAGE
                        + TextUi.LS + ClearCommand.MESSAGE_USAGE
                        + TextUi.LS + HelpCommand.MESSAGE_USAGE
                        + TextUi.LS + ExitCommand.MESSAGE_USAGE
        );
    }
}