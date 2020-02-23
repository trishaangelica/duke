package duke.data.task;

public class Deadlines extends Task {

  protected String dueDate;

  /**
   *  Represents a Deadline object.
   * @param description     description of deadline task
   * @param dueDate         due date of deadline task
   */
  public Deadlines(String description, String dueDate) {
    super(description);
    super.setTaskType("D");
    this.dueDate = dueDate;
  }

  public String getDueDate() {
    return dueDate;
  }

  @Override
  public String toString() {
    return "[" + super.getTaskType() + "]" + super.toString()  + "(by: " + getDueDate() + ")";
  }

  @Override
  public boolean isSameTask(Task toCheck) {
    return (toCheck == this) || (toCheck != null && toCheck.getDescription().equals(this.getDescription())
            && ((Deadlines) toCheck).getDueDate().equals(this.getDueDate()));
  }
}
