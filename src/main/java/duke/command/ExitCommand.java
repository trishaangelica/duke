package duke.command;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "bye";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exits the program.\n"
            + "|| Parameters: " + COMMAND_WORD
            + "\n"
            + "|| Example: " + COMMAND_WORD
            ;
    public static final String MESSAGE_EXIT_ACKNOWlEDGEMENT = "Exiting program ...";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_EXIT_ACKNOWlEDGEMENT);
    }

    public static boolean isExit(Command command) {
        return command instanceof ExitCommand; // instanceof returns false if it is null
    }
}
