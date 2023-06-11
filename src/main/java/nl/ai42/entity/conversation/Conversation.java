package nl.ai42.entity.conversation;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import nl.ai42.AI42Main;
import nl.ai42.utils.database.Row;

import java.io.Serializable;
import java.util.HashMap;


public class Conversation extends VBox implements Serializable {
    private transient Button button;
    private transient Font font;

    public Conversation(String name, Font font) {
        this.button = new Button(name);
        this.button.setPrefWidth(300);
        // Apply the same style as the startConversationButton
        this.button.setFont(font);
        this.font = font;
    }

    public void setOnAction(EventHandler<ActionEvent> consumer) {
        this.button.setOnAction(consumer);
    }

    public void saveToDataBase(final String conversation_count)
    {
        AI42Main.database.getTable("chat").insert(new Row(new HashMap<>() {{
            put("username", AI42Main.currentUser);
            put("chatname", "Conversation " + conversation_count);
        }}));
    }

    public Button getButton() {
        return this.button;
    }
}
