public class Task {
  protected String description;
  protected boolean isDone;
  protected String taskType;

  public Task(String description) {
    this.description = description;
    this.isDone = false;
    this.taskType = null;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public boolean isDone() {
    return isDone;
  }

  public void markAsDone() {
    isDone = true;
  }

  public String getStatusIcon() {
    return (isDone ? "\u2713" : "\u2718"); // return tick or X symbols
  }

  @Override
  public String toString() {
    return "[" + getStatusIcon() + "] " + description;
  }
}
