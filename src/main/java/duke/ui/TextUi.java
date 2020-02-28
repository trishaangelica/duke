package duke.ui;

import duke.command.CommandResult;
import duke.data.TaskList;
import java.io.InputStream;
import java.io.PrintStream;

import java.util.Scanner;

import static duke.common.Messages.*;

/**
 * Text UI of the application.
 */
public class TextUi {
    /**
     * A platform independent line separator.
     */
    public static final String LS = System.lineSeparator();

    public static final String DIVIDER = LS + " - - - - - - - - - - - - - - - - - - - - - - - - " + LS;
    private final Scanner in;
    private final PrintStream out;

    public TextUi() {
        this(System.in, System.out);
    }

    public TextUi(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Prompts for the command and reads the text entered by the user.
     * Ignores empty, pure whitespace, and comment lines.
     * Echos the command back to the user.
     *
     * @return command (full line) entered by the user
     */
    public String getUserCommand() {
        out.print("Enter command: ");
        String fullInputLine = in.nextLine();
        showToUser("[Command entered:" + fullInputLine + "]");
        return fullInputLine;
    }

    /**
     * Generates and prints the welcome message upon the start of the application.
     *
     * @param storageFilePath path to the storage file being used.
     */
    public void showWelcomeMessage(String storageFilePath) {
        String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
        showToUser(MESSAGE_WELCOME, storageFileInfo);
    }

    public void showGoodbyeMessage() {
        showToUser(MESSAGE_GOODBYE, DIVIDER);
    }

    public void showInitFailedMessage() {
        showToUser(MESSAGE_INIT_FAILED, DIVIDER);
    }

    /**
     * Shows message(s) to the user
     */
    public void showToUser(String... message) {
        for (String m : message) {
            out.println(m);
        }
    }

    public void showResultToUser(CommandResult result) {

        showToUser(result.feedbackToUser, DIVIDER);
    }


    public void showLoadedList() {
        if (TaskList.size() > 0) {
            System.out.println("\nGreat we're all loaded!\nHave a look at your previously saved list!" + DIVIDER);
            TaskList.showStoredTaskList();
            System.out.println(TextUi.DIVIDER + TextUi.LS);
        } else {
            System.out.println("\nSeems like we're starting from scratch ...\nI've created a new storage file for you!" + DIVIDER);

        }
    }
}
