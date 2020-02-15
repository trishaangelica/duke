package duke.task;

public class Task {

  public String description;
  protected boolean isDone;
  protected String taskType;

  /**
   * Represents a task object.
   * @param description       description of task
   */
  public Task(String description) {
    this.description = description;
    this.isDone = false;
    this.taskType = null;
  }

  public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public void markAsDone() {
    isDone = true;
  }

  public String getStatusIcon() {
    return (isDone ? "done" : "undone"); // return tick or X symbols
  }

  @Override
  public String toString() {
    return "-[" + getStatusIcon() + "] " + description;
  }
}
