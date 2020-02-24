package duke.common;


import duke.ui.TextUi;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String LOGO =
            " _ _ __                           \n" + "|_ _   |  _ _     _   __   _      _   _ _  _ _ _\n"
                    + "    "
                    + "|  | / _ \\   | |/ __|[   ]  [   ]|_=_||      |\n"
                    + " _  |  || | | \\  |  /     \\ \\   / /"
                    + "    _   \\  \\ /\n"
                    + "| |_|  || |_|  \\ |  |      \\ \\_/ /    | | | \\  \\\n"
                    + "|_ _ _ / " + "\\_ _/\\_\\[_ _]      \\__ /     |_| |_ _ _/";

    public static final String MESSAGE_GOODBYE = "Bye. Hope to see you again soon!";
    public static final String MESSAGE_INIT_FAILED = "Failed to initialise Jarvis application. Exiting...";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! "+ TextUi.LS + "%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASK_NOT_IN_LIST = "|| OOPS! I can't delete that because"
            + " you haven't" + " added task %s yet!";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = TextUi.LS + "\t%1$d tasks listed!";
    public static final String MESSAGE_WELCOME = TextUi.LS + "Hello from"
            + TextUi.LS + LOGO + "," + TextUi.LS + duke.ui.TextUi.DIVIDER
            + String.format("|             %-34s|" + TextUi.LS + "|       %-40s|", "Hello! I'm Jarvis :D",
            "What can I do for you today?") + duke.ui.TextUi.DIVIDER;
    public static final String MESSAGE_ERROR = "|| OOPS! The %s of a %s command cannot be empty.";
    public static final String MESSAGE_USING_STORAGE_FILE = "Using storage file : %1$s";
}
