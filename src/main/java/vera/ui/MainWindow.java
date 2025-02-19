package vera.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import vera.Vera;
import vera.core.Command;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Vera vera;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image veraImage = new Image(this.getClass().getResourceAsStream("/images/DaVera.png"));

    /**
     * Initializes the main window of the Vera chatbot.
     * Displays an initial greeting message from Vera in the dialog container.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getVeraDialog("Hello! I'm Vera. What can I do for you?", veraImage, Command.GREETING)
        );
    }

    /** Injects the Vera instance */
    public void setVera(Vera v) {
        vera = v;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Vera's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = vera.getResponse(input);
        Command returnEnum = getReturnType(input, response);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getVeraDialog(response, veraImage, returnEnum)
        );
        userInput.clear();

        if (input.equals("bye")) {
            Platform.exit();
            System.exit(0);
        }
    }

    private Command getReturnType(String input, String response) {
        if (response.startsWith("Oops")) {
            return Command.getCommandEnum(response);
        } else {
            return Command.getCommandEnum(input);
        }
    }
}
