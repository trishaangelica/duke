package duke.command;
import duke.storage.StorageFile;
import duke.ui.TextUi;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static duke.data.TaskList.clear;

/**
 * Clears the Task List.
 */

public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD +": Clears task list permanently." + TextUi.LS
            + "|| Parameter: " + COMMAND_WORD
            + TextUi.LS
            + "|| Example: " + COMMAND_WORD + TextUi.LS;

    public static final String MESSAGE_SUCCESS = TextUi.LS + "Task list has been cleared!";
    public static final String MESSAGE_FAIL = TextUi.LS + "Error clearing file: " + StorageFile.DEFAULT_STORAGE_FILEPATH;

    public ClearCommand() {
    }

    @Override
    public CommandResult execute() {
        clear();
        try {
            PrintWriter writer = new PrintWriter(StorageFile.DEFAULT_STORAGE_FILEPATH);
            writer.print("");
            writer.close();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (FileNotFoundException ioe) {
            return new CommandResult(MESSAGE_FAIL);
        }

    }
}