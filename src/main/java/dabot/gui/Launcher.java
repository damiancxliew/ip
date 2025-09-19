package dabot.gui;

import javafx.application.Application;

/**
 * Entry point class for launching the DaBot JavaFX application.
 */
public class Launcher {

    /**
     * Launches the JavaFX application by delegating to {@link MainApp}.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}

