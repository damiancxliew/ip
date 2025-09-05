package dabot.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for the main GUI window of DaBot.
 * Handles initialization, displaying messages, and user input events.
 */
public class MainWindow {
    @FXML private ScrollPane scrollPane;
    @FXML private TextArea dialogArea;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private MainApp app;

    /**
     * Initializes the window with the main app.
     *
     * @param app The main DaBot application instance.
     */
    public void init(MainApp app) {
        this.app = app;
        dialogArea.setEditable(false);
        dialogArea.setWrapText(true);
    }

    /** Displays the welcome message at startup. */
    public void showWelcome() {
        dialogArea.appendText("Hello! I'm DaBot\nWhat can I do for you?\n");
    }

    /** Handles user input, shows bot replies, and closes the window on "bye". */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;
        String reply = app.handle(input);

        dialogArea.appendText("\nYou: " + input + "\n");
        dialogArea.appendText("DaBot: " + reply + "\n");
        userInput.clear();

        if ("bye".equals(input)) {
            ((Stage) dialogArea.getScene().getWindow()).close(); // close window after reply
        }
    }
}
