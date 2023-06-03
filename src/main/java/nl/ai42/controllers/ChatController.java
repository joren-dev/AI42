package nl.ai42.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatController {

    @FXML
    private VBox conversationListContainer;
    @FXML
    private Button startConversationButton;

    private int conversationCount = 0;

    @FXML
    public void startConversation(ActionEvent actionEvent) {
        // Create a new conversation button
        Button newConversationButton = new Button("Conversation " + conversationCount);
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
        // Add your code here to open the conversation and start chatting
    }
    public void sendButtonAction(ActionEvent actionEvent) {
        // Handle send button action
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