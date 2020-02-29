package duke.data.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Events extends Task {

  protected String timeOfEvent;
  protected LocalDateTime eventTime;

  /**
   * Represents an event object.
   *
   * @param description description of event
   * @param timeOfEvent time of event
   */
  public Events(String description, String timeOfEvent) {
    super(description.trim());
    super.setTaskType("E");
    this.timeOfEvent = timeOfEvent;
  }

  public LocalDateTime getEventTime() {
    return eventTime;
  }

  public Events(String description, LocalDateTime eventTime) {
    super(description);
    super.setTaskType("E");
    super.setDate(eventTime);
    this.eventTime = eventTime;

  }


  public String getTimeOfEvent() {
    return timeOfEvent;
  }

  @Override
  public String toString() {
    if (eventTime != null) {
      DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
      return "[" + super.getTaskType() + "]" + super.toString()
              + " (at: " + getEventTime().format(newPattern) + ")";
    } else {
      return "[" + super.getTaskType() + "]" + super.toString() + " (at: " + getTimeOfEvent()
              + ")";
    }
  }

  @Override
  public boolean isSameTask(Task toCheck) {
    if(eventTime!=null){
      return (toCheck == this) || (toCheck != null && toCheck.getDescription().equals(this.getDescription())
              && ((Events) toCheck).getEventTime().equals(this.getEventTime()));
    }
      return (toCheck == this) || (toCheck != null && toCheck.getDescription().equals(this.getDescription())
      && ((Events) toCheck).getTimeOfEvent().equals(this.getTimeOfEvent()));
  }
}
