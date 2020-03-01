import duke.command.Command;
import duke.command.CommandResult;
import duke.command.ExitCommand;
import duke.data.TaskList;
import duke.parser.Parser;
import duke.storage.StorageFile;
import duke.storage.StorageFile.StorageFilePathException;
import duke.ui.TextUi;

public class Duke {

    private TextUi ui;
    public StorageFile storage;
    private TaskList taskList;


    public static void main(String... launchArgs) {
        new Duke().run(launchArgs);
    }

    /** Runs the program until termination.  */
    public void run(String[] launchArgs) {
        start(launchArgs);
        runCommandLoopUntilExitCommand();
        exit();
    }
    private void start(String[] launchArgs) {
        try {
            this.ui = new TextUi();
            this.storage = initializeStorage(launchArgs);
            this.taskList = storage.load();
            ui.showWelcomeMessage(storage.getPath());
            ui.showLoadedList();

        } catch (StorageFile.StorageOperationException | StorageFilePathException e) {
            ui.showInitFailedMessage();
            throw new RuntimeException(e);
        }
    }
    /** Prints the Goodbye message and exits. */
    private void exit() {
        ui.showGoodbyeMessage();
        System.exit(0);
    }
    /** Reads the user command and executes it, until the user inputs the exit command.  */
    private void runCommandLoopUntilExitCommand() {
        Command command;
        do {
            String userCommandText = ui.getUserCommand();
            command = new Parser().parseCommand(userCommandText);
            CommandResult result = executeCommand(command);
            ui.showResultToUser(result);
            saveListToStorageFile(taskList);

        } while (!ExitCommand.isExit(command));
    }

    private void saveListToStorageFile(TaskList taskList){
        try{
            storage.save(taskList);
        } catch (StorageFile.StorageOperationException e){
            System.out.println("Error saving data to storage file..");
        }
    }


    /**
     * Executes the command and returns the result.
     *
     * @param command user command
     * @return result of the command
     */
    private CommandResult executeCommand(Command command) {
        try {
            command.setData(taskList);
            return command.execute();
        } catch (Exception e) {
            ui.showToUser(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the StorageFile object based on the user specified path (if any) or the default storage path.
     * @param launchArgs arguments supplied by the user at program launch
     * @throws StorageFilePathException if the target file path is incorrect.
     */
    private StorageFile initializeStorage(String[] launchArgs) throws StorageFilePathException {
        boolean isStorageFileSpecifiedByUser = launchArgs.length > 0;
        return isStorageFileSpecifiedByUser ? new StorageFile(launchArgs[0]) : new StorageFile();
    }

}
