package dabot.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class for the main GUI window of DaBot.
 * Supports asymmetric chat bubbles with error highlighting.
 * Falls back to a plain TextArea transcript if no VBox dialogContainer is present in FXML.
 */
public class MainWindow {
    // If your FXML has a VBox inside the ScrollPane, bind it here (preferred).
    @FXML private VBox dialogContainer;

    @FXML private ScrollPane scrollPane;
    @FXML private TextArea dialogArea; // kept for backward-compat; used if dialogContainer is absent
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

        // Old TextArea mode (fallback)
        if (dialogArea != null) {
            dialogArea.setEditable(false);
            dialogArea.setWrapText(true);
        }

        // Auto-scroll when using bubble mode
        if (scrollPane != null && dialogContainer != null) {
            scrollPane.setFitToWidth(true);
            // Always scroll to bottom when new content is added
            dialogContainer.heightProperty().addListener((obs, oldH, newH) ->
                    scrollPane.setVvalue(1.0));
        }
    }

    /** Displays the welcome message at startup. */
    public void showWelcome() {
        String welcome = "Hello! I'm DaBot\nWhat can I do for you?";
        if (isBubbleMode()) {
            addBubble(welcome, false, false);
        } else if (dialogArea != null) {
            dialogArea.appendText(welcome + "\n");
        }
    }

    /** Handles user input, shows bot replies, and closes the window on "bye". */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText() == null ? "" : userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        // Ask app to handle and produce a reply
        String reply = app.handle(input);

        // Decide if reply should be highlighted as error
        boolean isError = looksLikeError(reply);

        // Render
        if (isBubbleMode()) {
            addBubble(input, true, false);
            addBubble(reply, false, isError);
            Platform.runLater(() -> {
                if (scrollPane != null) {
                    scrollPane.setVvalue(1.0);
                }
            });
        } else if (dialogArea != null) {
            dialogArea.appendText("\nYou: " + input + "\n");
            dialogArea.appendText("DaBot: " + reply + "\n");
        }

        userInput.clear();

        if ("bye".equals(input)) {
            // Close after showing reply
            Stage stage = (Stage) (userInput != null ? userInput.getScene().getWindow()
                    : (scrollPane != null ? scrollPane.getScene().getWindow() : null));
            if (stage != null) {
                stage.close();
            }
        }
    }

    private boolean isBubbleMode() {
        return dialogContainer != null;
    }

    private void addBubble(String text, boolean isUser, boolean isError) {
        Label msg = new Label(text);
        msg.setWrapText(true);
        msg.setMinHeight(Region.USE_PREF_SIZE); // prevents vertical clipping on wrapped labels
        HBox.setHgrow(msg, Priority.ALWAYS);

        // keep some horizontal margin so lines break nicely
        if (scrollPane != null) {
            msg.maxWidthProperty().bind(dialogContainer.widthProperty().subtract(40));
        } else {
            msg.setMaxWidth(320); // fallback
        }

        // simple styling samples; adjust your CSS as needed
        msg.getStyleClass().add(isUser ? "bubble-user" : "bubble-bot");
        if (isError) {
            msg.getStyleClass().add("bubble-error");
        }

        HBox bubble = new HBox(msg);
        bubble.setFillHeight(true);
        bubble.setSpacing(6);
        bubble.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        dialogContainer.getChildren().add(bubble);

        // ensure we scroll after layout pass
        Platform.runLater(() -> {
            if (scrollPane != null) {
                scrollPane.setVvalue(1.0);
            }
        });
    }


    private boolean looksLikeError(String reply) {
        if (reply == null) {
            return false;
        }
        String r = reply.trim();
        // simple heuristics to highlight validation errors
        return r.startsWith("Please ")
                || r.startsWith("Task number")
                || r.startsWith("Date ")
                || r.startsWith("Unknown")
                || r.startsWith("Grrrr")
                || r.endsWith("cannot be empty.")
                || r.contains("must be")
                || r.contains("invalid")
                || r.contains("Error");
    }
}
