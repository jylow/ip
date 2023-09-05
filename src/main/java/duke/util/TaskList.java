package duke.util;

import java.util.ArrayList;

import duke.exception.DukeException;
import duke.exception.InvalidTaskNumberException;
import duke.exception.NoTaskException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

/**
 * Tasklist containing the tasks added by the user.
 */
public class TaskList {
    private final ArrayList<Task> tasklst;

    /**
     * Constructor for an instance of an empty tasklist.
     */
    public TaskList() {
        this.tasklst = new ArrayList<>();
    }

    /**
     * Constructor for a tasklist with a string of tasks.
     * @param fileData ArrayList of strings with each string being a task to be added into the tasklist
     * @throws DukeException exceptions that will be encountered when trying to add tasks
     */
    public TaskList(ArrayList<String> fileData) throws DukeException {
        String[] fields;
        this.tasklst = new ArrayList<>();
        for (String info : fileData) {
            fields = info.split(" \\| ");
            Task tempT;
            switch (fields[0].trim()) {
            case "T":
                if (fields[2].isEmpty()) {
                    throw new NoTaskException("Error! Cannot add an empty todo!");
                }
                tempT = new ToDo(fields[2].trim());
                if (fields[1].equals("1")) {
                    tempT.markAsDone();
                }
                tasklst.add(tempT);
                break;
            case "D":
                tempT = new Deadline(fields[2].trim(), fields[3]);
                if (fields[1].equals("1")) {
                    tempT.markAsDone();
                }
                tasklst.add(tempT);
                break;
            case "E":
                String[] time;
                time = fields[3].split("->");
                tempT = new Event(fields[2].trim(), time[0], time[1]);
                if (fields[1].equals("1")) {
                    tempT.markAsDone();
                }
                tasklst.add(tempT);
                break;
            default:
            }
        }
    }

    /**
     * Marks a task within the tasklist as complete.
     * @param taskNumber task number to be marked
     * @param ui instance of user interface
     * @throws InvalidTaskNumberException Error when given task number exceeds the number of tasks in the list
     */
    public void markTask(int taskNumber, Ui ui) throws InvalidTaskNumberException {
        if (taskNumber > tasklst.size()) {
            throw new InvalidTaskNumberException("Error! Task Number given is outside range of current list size of "
                    + tasklst.size());
        }
        tasklst.get(taskNumber - 1).markAsDone();
        ui.showMarked(tasklst.get(taskNumber - 1));
    }

    /**
     * Unmarks a task with the given task number in the tasklist.
     * @param taskNumber task number to be unmarked
     * @param ui instance of user interface
     * @throws InvalidTaskNumberException exception when the task number given is outside the count of tasklist
     */
    public void unmarkTask(int taskNumber, Ui ui) throws InvalidTaskNumberException {
        if (taskNumber > tasklst.size()) {
            throw new InvalidTaskNumberException("Error! Task Number given is outside range of current list size of "
                    + tasklst.size());
        } else {
            tasklst.get(taskNumber - 1).unmarkAsDone();
            ui.showUnmarked(tasklst.get(taskNumber - 1));
        }
    }

    /**
     * Adds given task to the tasklist.
     * @param task Task to be added to the list
     * @param ui instance of user interface
     */
    public void addTask(Task task, Ui ui) {
        tasklst.add(task);
        ui.showTaskAdded(task, tasklst.size());
    }

    /**
     * Deletes a task from the tasklist.
     * @param taskNumber Task number of task to be deleted
     * @param ui instance of user interface
     * @throws InvalidTaskNumberException Exception when given task number is outside range of tasks in the list
     */
    public void deleteTask(int taskNumber, Ui ui) throws InvalidTaskNumberException {
        if (taskNumber > tasklst.size()) {
            throw new InvalidTaskNumberException("Error! Task Number given is outside range of current list size of "
                    + tasklst.size());
        } else {
            Task temp = tasklst.remove(taskNumber - 1);
            ui.showComplete("Noted. I've removed this task:"
                    + temp
                    + "Now you have " + this.tasklst.size() + " task(s) in the list");
        }
    }

    /**
     * Returns tasks in the tasklist in format to be written into a file.
     * @return ArrayList of formatted strings to be written
     */
    public ArrayList<String> toWriteFormat() {
        ArrayList<String> tasks = new ArrayList<>();
        for (Task task : tasklst) {
            tasks.add(task.toSaveFormat());
        }
        return tasks;
    }

    /**
     * Returns string representations of tasks in an arraylist to be printed by the UI.
     * @return ArrayList of strings to be printed onto ui
     */
    public ArrayList<String> getTaskStrings() {
        ArrayList<String> listOfStrings = new ArrayList<>();
        for (Task task : tasklst) {
            listOfStrings.add(task.toString());
        }
        return listOfStrings;
    }

    /**
     * Filters tasklist to search for a keyword.
     * @param keyword keyword to search if task description contains
     * @return Arraylist containing filtered tasks
     */
    public ArrayList<String> filterToList(String keyword) {
        ArrayList<String> filteredList = new ArrayList<>();
        for (Task task : tasklst) {
            if (task.contains(keyword)) {
                filteredList.add(task.toString());
            }
        }
        return filteredList;
    }
}
