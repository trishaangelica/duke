package duke.data.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Deadlines extends Task {

    protected String dueDate;
    protected LocalDateTime date;

    /**
     * Represents a Deadline object.
     *
     * @param description description of deadline task
     * @param dueDate     due date of deadline task
     */
    public Deadlines(String description, String dueDate) {
        super(description);
        super.setTaskType("D");
        this.dueDate = dueDate;
    }


    public Deadlines(String description, LocalDateTime date) {
        super(description);
        super.setTaskType("D");
        super.setDate(date);
        this.date = date;

    }

    public LocalDateTime getDate() {
        return date;
    }


    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        if (date != null) {
            DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
            return "[" + super.getTaskType() + "]" + super.toString()
                    + " (by: " + getDate().format(newPattern) + ")";
        } else {
            return "[" + super.getTaskType() + "]" + super.toString() + " (by: " + getDueDate() + ")";
        }
    }

    @Override
    public boolean isSameTask(Task toCheck) {
        if (date != null) {
            return (toCheck == this) || (toCheck != null && toCheck.getDescription().equals(this.getDescription())
                    && toCheck.getDate().equals(this.getDate()));
        } else {
            return (toCheck == this) || (toCheck != null && toCheck.getDescription().equals(this.getDescription())
                    && ((Deadlines)toCheck).getDueDate().equals(this.getDueDate()));
        }
    }
}

