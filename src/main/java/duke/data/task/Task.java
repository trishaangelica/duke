package duke.data.task;

import java.time.LocalDateTime;

public class Task implements Comparable<Task> {

  public String description;
  public boolean isDone;
  public String taskType;
  public LocalDateTime date;

  /**
   * Represents a task object.
   *
   * @param description description of task
   */
  public Task(String description) {
    this.description = description.trim();
    this.isDone = false;
    this.taskType = null;
    this.date = null;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
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

  public String getStatus() {
    return (isDone ? "done" : "undone"); // return tick or X symbols
  }

  @Override
  public String toString() {
    return "-[" + getStatus() + "] " + description;
  }


  public boolean isSameTask(Task toCheck) {
    return (toCheck == this)
            || (toCheck != null
            && toCheck.getDescription().equals(this.getDescription()));
  }

  @Override
  public int compareTo(Task o) {
    if (getDate() == null || o.getDate() == null)
      return 0;
    return getDate().compareTo(o.getDate());
  }
}
