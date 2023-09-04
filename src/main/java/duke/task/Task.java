package duke.task;
/**
 * Task encapsulate a task with a String description and boolean isDone. It supports
 * marking the completion of tasks
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with a given description. Completion of the task
     * is false by default.
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Save format function to be overridden.
     * @return null in case of a Task
     */
    public String toSaveFormat() {
        return null;
    }

    /**
     * Returns the completion status of task with "X" meaning task is completed.
     * @return "X" if completed " " if still in progress
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns a formatted string of the status of the task.
     * @return String containing completion status and description of task
     */
    public String toString() {
        return " [" + getStatusIcon() + "] " + this.description;
    }

    /**
     * Marks the current task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmarks the current task as not completed.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Checks if description of task contains given keyword.
     * @param keyword keyword to be checked
     * @return true if contains keyword else false
     */
    public boolean contains(String keyword) {
        return this.description.contains(keyword);
    }
}