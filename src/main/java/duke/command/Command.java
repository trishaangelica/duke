package duke.command;

public class Command {

    private String commandName;
    private String args;

    public Command(String command, String args) {
        this.commandName = command;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }

}