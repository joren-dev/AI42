package nl.ai42.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
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
    private transient VBox chatPane;
    @FXML
    private transient VBox conversationListContainer;
    @FXML
    private transient Button startConversationButton;
    @FXML
    private transient TextArea messageBox;
    public static String currentConversation;

    private int conversationCount = 0;

    @FXML
    public void startConversation(ActionEvent actionEvent) {
        // Create a new conversation button
        Button newConversationButton = new Button("Conversation " + conversationCount);

        AI42Main.database.getTable("chat").insert(new Row(new HashMap<>() {{
            put("username", AI42Main.currentUser);
            put("chatname", "Conversation " + conversationCount);
        }}));


        currentConversation = "Conversation " + conversationCount;
        newConversationButton.setPrefWidth(300);
        newConversationButton.setOnAction(this::openConversation);

        // Apply the same style as the startConversationButton
        newConversationButton.setFont(startConversationButton.getFont());

        // Set the spacing between conversations
        conversationListContainer.setSpacing(10);

        // Add the new conversation button at the bottom
        conversationListContainer.getChildren().add(newConversationButton);

        conversationCount++;
    }

    private void openConversation(ActionEvent actionEvent) {
        // Retrieve the clicked conversation button
        Button conversationButton = (Button) actionEvent.getSource();

        // Get the conversation name from the button's text
        String conversationName = conversationButton.getText();

        // Perform actions to open the conversation and start chatting
        System.out.println("Opening conversation: " + conversationName);
        currentConversation = conversationName;
        // Add your code here to open the conversation and start chatting
        chatPane.getChildren().clear();
        ArrayList<Row> rows = AI42Main.database.getTable("chatmsg").select((row) -> row.getValue("username").equals(AI42Main.currentUser) && row.getValue("chatname").equals(currentConversation));

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
        chatPane.getChildren().add(messagePairContainer);

        // Add spacing at the end of each message pair
        Region spacing = new Region();
        spacing.setMinHeight(10);
        chatPane.getChildren().add(spacing);
    }

    public void sendButtonAction(ActionEvent actionEvent) {
        String aiResponse = AIManager.ask(messageBox.getText());
        addQuestionAndAnswer(messageBox.getText(), aiResponse);

        AI42Main.database.getTable("chatmsg").insert(new Row(new HashMap<>() {{
            put("username", AI42Main.currentUser);
            put("chatname", currentConversation);
            put("msg_counter", String.valueOf(AI42Main.database.getTable("chatmsg").select((row) -> true).size() + 1));
            put("msg_content", messageBox.getText());
            put("is_ai", "false");
            put("sent", new Date().toString());
        }}));
        AI42Main.database.getTable("chatmsg").insert(new Row(new HashMap<>() {{
            put("username", AI42Main.currentUser);
            put("chatname", currentConversation);
            put("msg_counter", String.valueOf(AI42Main.database.getTable("chatmsg").select((row) -> true).size() + 1));
            put("msg_content", aiResponse);
            put("is_ai", "true");
            put("sent", new Date().toString());
        }}));
    }
    public void updateDisplayedMessages()
    {

    }

    public void sendMethod(KeyEvent keyEvent) {
        // Handle send method
    }

    public void handleExitButtonClick(MouseEvent mouseEvent) {
        Stage window = (Stage) conversationListContainer.getScene().getWindow();
        window.close();

        try {
            AI42Main.database.storeInFile("AI42.db");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleSettingsButton(MouseEvent mouseEvent) {
    }
}