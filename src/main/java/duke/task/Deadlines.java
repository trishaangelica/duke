package duke.task;

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
    return "[" + super.getTaskType() + "]" + super.toString() + "(by: " + getDueDate() + ")";
  }
}
