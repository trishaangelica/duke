public class Command {
  private String CommandName;
  private String Args;

  public Command(String Command, String Args) {
    this.CommandName = Command;
    this.Args = Args;
  }

  public String getCommandName() {
    return CommandName;
  }

  public void setCommandName(String commandName) {
    CommandName = commandName;
  }

  public String getArgs() {
    return Args;
  }

  public void setArgs(String args) {
    Args = args;
  }
  }