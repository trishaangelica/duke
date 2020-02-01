public class ToDos extends Task {

  public ToDos(String description) {
    super(description);
    super.setTaskType("T");
  }

  @Override
  public String toString() {
    return "[" + super.getTaskType() + "]" + super.toString();
  }
}
