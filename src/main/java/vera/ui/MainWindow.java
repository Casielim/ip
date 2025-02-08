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

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getVeraDialog("Hello! I'm Vera. What can I do for you?", veraImage, "greeting")
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
        String commandType = vera.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getVeraDialog(response, veraImage, commandType)
        );
        userInput.clear();

        if (input.equals("bye")) {
            Platform.exit();  // Close the JavaFX application
            System.exit(0);   // Terminate the program completely
        }
    }
}