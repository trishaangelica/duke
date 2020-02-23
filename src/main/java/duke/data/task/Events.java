package duke.data.task;

public class Events extends Task {

  protected String timeOfEvent;

  /**
   * Represents an event object.
   *
   * @param description description of event
   * @param timeOfEvent time of event
   */
  public Events(String description, String timeOfEvent) {
    super(description);
    super.setTaskType("E");
    this.timeOfEvent = timeOfEvent;
  }

  public String getTimeOfEvent() {
    return timeOfEvent;
  }

  @Override
  public String toString() {
    return "[" + super.getTaskType() + "]" + super.toString() + "(at: " + getTimeOfEvent()
            + ")";
  }

  @Override
  public boolean isSameTask(Task toCheck) {
      return (toCheck == this) || (toCheck != null && toCheck.getDescription().equals(this.getDescription())
      && ((Events) toCheck).getTimeOfEvent().equals(this.getTimeOfEvent()));
  }
}
