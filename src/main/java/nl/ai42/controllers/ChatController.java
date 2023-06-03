package nl.ai42.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.ai42.AI42Main;
import nl.ai42.utils.Row;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatController {

    @FXML
    private VBox chatPane;
    @FXML
    private VBox conversationListContainer;
    @FXML
    private Button startConversationButton;
    @FXML
    private TextArea messageBox;
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
    }
    public void sendButtonAction(ActionEvent actionEvent) {
        // Handle send button action
        ArrayList<Row> data = AI42Main.database.getTable("chatmsg").select((row) -> true);
        int counter;
        if (data.size() == 0)
            counter = 0;
        else
            counter = Integer.parseInt(data.get(data.size() - 1).getValue("msg_counter"));
        counter++;
        int finalCounter = counter;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        AI42Main.database.getTable("chatmsg").insert(new Row(new HashMap<>() {{
            put("username", AI42Main.currentUser);
            put("chatname", currentConversation);
            put("msg_counter", String.valueOf(finalCounter));
            put("msg_content", messageBox.getText());
            put("is_ai", "false");
            put("sent", format.format(new Date()));
        }}));

        ArrayList<Row> rows = AI42Main.database.getTable("chatmsg").select((row) -> row.getValue("username").equals(AI42Main.currentUser) && row.getValue("chatname").equals(currentConversation));

        StringBuilder conversationHistory = new StringBuilder();
        for (Row row : rows) {
            conversationHistory.append(row.getValue("msg_content")).append("\n------------------------------------------------------------------------\n");
        }
        String conversation = conversationHistory.toString();

        if (chatPane.getChildren().size() != 0) {
            chatPane.getChildren().remove(0);
        }
        Label newMsg = new Label(conversation);
        chatPane.getChildren().add(newMsg);

        messageBox.setText("");
    }

    public void sendMethod(KeyEvent keyEvent) {
        // Handle send method
    }

    public void handleExitButtonClick(MouseEvent mouseEvent) {
        Stage window = (Stage) conversationListContainer.getScene().getWindow();
        window.close();
    }

    public void handleSettingsButton(MouseEvent mouseEvent) {
    }
}