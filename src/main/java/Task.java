public class Task {
  protected String description;
  protected boolean isDone;

  public Task(String description) {
    this.description = description;
    this.isDone = false;
  }

  public String getDescription() {
    return description;
  }

  public boolean isDone() {
    return isDone;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void markAsDone() {
    isDone = true;
  }

  public String getStatusIcon() {
    return (isDone ? "\u2713" : "\u2718"); // return tick or X symbols
  }
}
