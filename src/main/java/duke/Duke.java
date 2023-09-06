package duke;

import java.io.IOException;

import duke.command.Command;
import duke.exception.DukeException;
import duke.util.Parser;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

/**
 * This program is a chat-bot, B055man, used to mark completion of tasks
 * marking the completion of tasks.
 */
public class Duke {

    private final Storage storage;
    private TaskList tasklist;
    private final Ui ui;

    /**
     * Duke class that initialises a B055man Chatbot.
     * @param filePath path location of the file to read previous stores
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasklist = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasklist = new TaskList();
        }
    }

    /**
     * Runs the B055man bot.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.read();
                ui.showLine();
                Command c = Parser.parseUserInput(fullCommand);
                c.execute(tasklist, ui, storage);
                isExit = c.isExit();
            } catch (DukeException | IOException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * The program reads input given by the user to perform functions to help
     * add, edit, track and delete tasks inputted.
     */
    public static void main(String[] args) {
        new Duke("data/tasks.txt").run();
    }
}
