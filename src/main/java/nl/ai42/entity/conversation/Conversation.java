package nl.ai42.entity.conversation;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import java.util.function.Function;


public class Conversation extends VBox {
    private Button button;

    public Conversation(Button button) {
        this.button = button;
        button.setPrefWidth(300);
    }

    public void setOnAction(Function<ActionEvent, Void> function) {

    }
}
