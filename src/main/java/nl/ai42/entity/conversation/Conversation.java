package nl.ai42.entity.conversation;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import javafx.scene.text.Font;

import java.util.function.Consumer;


public class Conversation extends VBox {
    private Button button;

    public Conversation(Button button, Font font) {
        this.button = button;
        this.button.setPrefWidth(300);
        // Apply the same style as the startConversationButton
        this.button.setFont(font);
    }

    public void setOnAction(EventHandler<ActionEvent> consumer) {
        this.button.setOnAction(consumer);
    }

    public Button getButton() {
        return this.button;
    }
}
