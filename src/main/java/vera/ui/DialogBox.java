package vera.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import vera.core.Command;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a dialog box representing the user's input.
     *
     * @param text The yser input text.
     * @param img The user's profile image.
     * @return A dialogBox containing user input.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    private void changeDialogStyle(Command commandEnum) {
        switch(commandEnum) {
        case LIST:
            dialog.getStyleClass().add("list-label");
            break;
        case MARK:
            dialog.getStyleClass().add("mark-label");
            break;
        case UNMARK:
            dialog.getStyleClass().add("unmark-label");
            break;
        case DELETE:
            dialog.getStyleClass().add("delete-label");
            break;
        case FIND:
            dialog.getStyleClass().add("find-label");
            break;
        case ADD:
            dialog.getStyleClass().add("add-label");
            break;
        case OOPS:
            dialog.getStyleClass().add("error-label");
            break;
        default:
            // Do nothing
        }
    }

    /**
     * Creates a dialog box representing Vera's response and applies corresponding styling based on the command type.
     *
     * @param response The response text from Vera.
     * @param img Vera chatbot's profile image.
     * @param commandEnum The tyoe of the command.
     * @return A DialogBox containing Vera's response.
     */
    public static DialogBox getVeraDialog(String response, Image img, Command commandEnum) {
        var db = new DialogBox(response, img);
        db.flip();
        db.changeDialogStyle(commandEnum);
        return db;
    }

}
