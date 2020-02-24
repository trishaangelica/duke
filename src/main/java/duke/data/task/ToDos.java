package duke.data.task;

public class ToDos extends Task {

    public ToDos(String description) throws NullPointerException {
        super(description);
        super.setTaskType("T");
        super.setDate(null);
    }

    @Override
    public String toString() {
        return "[" + super.getTaskType() + "]" + super.toString();
    }

    @Override
    public boolean isSameTask(Task toCheck) {
        return (toCheck == this)
                || (toCheck != null
                && toCheck.getDescription().equals(this.getDescription()));
    }
}
