package duke.data.task;

public class Task {

  public String description;
  public boolean isDone;
  public String taskType;



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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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


  public  boolean isSameTask(Task toCheck){
    return (toCheck == this)
            || (toCheck != null
            && toCheck.getDescription().equals(this.getDescription()));
  }
}
