package nl.ai42.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.ai42.AI42Main;
import nl.ai42.managers.AIManager;
import nl.ai42.utils.Row;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatController implements Serializable {

    @FXML
    private transient VBox chat_panel;
    @FXML
    private transient VBox conversation_list_container;
    @FXML
    private transient Button start_conversation_button;
    @FXML
    private transient TextArea message_box;

    public static String current_conversation;
    private int conversation_count = 0;

    @FXML
    public void startConversation(ActionEvent action_event)
    {
        // Create a new conversation button
        final Button newConversationButton = new Button("Conversation " + conversation_count);

        AI42Main.database.getTable("chat").insert(new Row(new HashMap<>() {{
            put("username", AI42Main.currentUser);
            put("chatname", "Conversation " + conversation_count);
        }}));

        try {
            AI42Main.database.storeInFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        current_conversation = "Conversation " + conversation_count;
        newConversationButton.setPrefWidth(300);
        newConversationButton.setOnAction(this::openConversation);

        // Apply the same style as the startConversationButton
        newConversationButton.setFont(start_conversation_button.getFont());

        // Set the spacing between conversations
        conversation_list_container.setSpacing(10);

        // Add the new conversation button at the bottom
        conversation_list_container.getChildren().add(newConversationButton);

        conversation_count++;
    }

    private void openConversation(ActionEvent actionEvent) {
        // Retrieve the clicked conversation button
        Button conversationButton = (Button) actionEvent.getSource();

        // Get the conversation name from the button's text
        String conversationName = conversationButton.getText();

        // Perform actions to open the conversation and start chatting
        System.out.println("Opening conversation: " + conversationName);
        current_conversation = conversationName;
        // Add your code here to open the conversation and start chatting
        chat_panel.getChildren().clear();
        ArrayList<Row> rows = AI42Main.database.getTable("chatmsg").select((row) -> row.getValue("username").equals(AI42Main.currentUser) && row.getValue("chatname").equals(current_conversation));

        HBox questionBox = new HBox();
        questionBox.getStyleClass().add("question");
        questionBox.setPadding(new Insets(10, 0, 5, 0)); // Add padding between question and answer

        String question = "";
        String answer;
        for (Row row : rows) {
            if (row.getValue("is_ai").equals("false")) {
                question = row.getValue("msg_content");
            } else {
                answer = row.getValue("msg_content");
                addQuestionAndAnswer(question, answer);
            }
        }
    }

    public void addQuestionAndAnswer(String query, String response) {
        // Create question box
        HBox questionBox = new HBox();
        questionBox.getStyleClass().add("question");
        questionBox.setPadding(new Insets(10, 0, 5, 0)); // Add padding between question and answer

        Text question = new Text(query);
        question.wrappingWidthProperty().bind(questionBox.widthProperty().multiply(.8));

        questionBox.getChildren().addAll(question);

        // Create answer box
        HBox answerBox = new HBox();
        answerBox.getStyleClass().add("answer");

        Text answer = new Text(response);
        answerBox.getChildren().addAll(answer);
        answer.wrappingWidthProperty().bind(answerBox.widthProperty().multiply(.8));

        // Create message pair container
        VBox messagePairContainer = new VBox();
        messagePairContainer.setSpacing(5); // Add spacing between question and answer
        messagePairContainer.getChildren().addAll(questionBox, answerBox);

        // Add message pair container to chatPane
        chat_panel.getChildren().add(messagePairContainer);

        // Add spacing at the end of each message pair
        Region spacing = new Region();
        spacing.setMinHeight(10);
        chat_panel.getChildren().add(spacing);
    }

    public void sendButtonAction(ActionEvent actionEvent) {
        String aiResponse = AIManager.ask(message_box.getText());
        addQuestionAndAnswer(message_box.getText(), aiResponse);

        AI42Main.database.getTable("chatmsg").insert(new Row(new HashMap<>() {{
            put("username", AI42Main.currentUser);
            put("chatname", current_conversation);
            put("msg_counter", String.valueOf(AI42Main.database.getTable("chatmsg").select((row) -> true).size() + 1));
            put("msg_content", message_box.getText());
            put("is_ai", "false");
            put("sent", new Date().toString());
        }}));
        AI42Main.database.getTable("chatmsg").insert(new Row(new HashMap<>() {{
            put("username", AI42Main.currentUser);
            put("chatname", current_conversation);
            put("msg_counter", String.valueOf(AI42Main.database.getTable("chatmsg").select((row) -> true).size() + 1));
            put("msg_content", aiResponse);
            put("is_ai", "true");
            put("sent", new Date().toString());
        }}));

        try {
            AI42Main.database.storeInFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDisplayedMessages()
    {

    }

    public void handleExitButtonClick(MouseEvent mouseEvent) {
        Stage window = (Stage) conversation_list_container.getScene().getWindow();
        window.close();

        try {
            AI42Main.database.storeInFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleSettingsButton(MouseEvent mouseEvent) {
    }
}