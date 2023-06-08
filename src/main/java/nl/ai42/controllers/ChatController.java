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
import nl.ai42.entity.conversation.Conversation;
import nl.ai42.managers.AIManager;
import nl.ai42.managers.ConversationManager;
import nl.ai42.managers.SceneManager;
import nl.ai42.utils.Row;

import java.util.ArrayList;

public class ChatController {
    @FXML
    private VBox chat_panel;
    @FXML
    private VBox conversation_list_container;
    @FXML
    private Button start_conversation_button;
    @FXML
    private TextArea message_box;
    public static String current_conversation;
    private int conversation_count = 0;
    private ConversationManager conversation_manager;

    @FXML
    public void initialize()
    {
        // Initialize manager
        conversation_manager = new ConversationManager();

        final int conversationCount = conversation_manager.getConversationCount();

        // Create the amount of conversations based on the user's conversations
        for (int i = 0; i < conversationCount; i++)
            this.startConversation(new ActionEvent(), false);

        // TODO: set conversation_count to same amount - 1?
    }

    @FXML
    public void startConversation(ActionEvent actionEvent) {
        startConversation(actionEvent, true);
    }
    @FXML
    public void startConversation(ActionEvent action_event, boolean create)
    {
        // Create a new conversation button
        final Conversation convo = conversation_manager.startConversation(new Conversation(
                "Conversation " + conversation_count, start_conversation_button.getFont())
        );

        // In case the convo is new and doesn't exist in DB, then create new one in DB.
        if (create)
            convo.saveToDataBase(Integer.toString(conversation_count));

        // Store new or previous conversations (in case they changed contents) in DB
        AI42Main.database.storeInFile();

        // Set current conversation to newly created convo
        current_conversation = "Conversation " + conversation_count;

        // Set method on what to do if the convo is clicked on (opening the convo)
        convo.setOnAction(this::openConversation);

        // Set the spacing between conversations
        conversation_list_container.setSpacing(10);

        // Add the new conversation button at the bottom
        conversation_list_container.getChildren().add(convo.getButton());

        conversation_count++;
    }

    private void openConversation(ActionEvent action_event) {
        // Retrieve the clicked conversation button
        // Get the conversation name from the button's text
        final String conversation_name = ((Button) action_event.getSource()).getText();

        // Perform actions to open the conversation and start chatting
        System.out.println("Opening conversation: " + conversation_name);
        current_conversation = conversation_name;

        chat_panel.getChildren().clear();

        // Get chat history from selected conversation
        final ArrayList<Row> rows = conversation_manager.getChatHistoryFromConversation(current_conversation);

        final HBox question_box = new HBox();
        question_box.getStyleClass().add("question");
        question_box.setPadding(new Insets(10, 0, 5, 0)); // Add padding between question and answer

        String question = "";
        String answer;
        for (final Row row : rows) {
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
        HBox question_box = new HBox();
        question_box.getStyleClass().add("question");
        question_box.setPadding(new Insets(10, 0, 5, 0)); // Add padding between question and answer

        Text question = new Text(query);
        question.wrappingWidthProperty().bind(question_box.widthProperty().multiply(.8));

        question_box.getChildren().addAll(question);

        // Create answer box
        HBox answer_box = new HBox();
        answer_box.getStyleClass().add("answer");

        Text answer = new Text(response);
        answer_box.getChildren().addAll(answer);
        answer.wrappingWidthProperty().bind(answer_box.widthProperty().multiply(.8));

        // Create message pair container
        VBox message_pair_container = new VBox();
        message_pair_container.setSpacing(5); // Add spacing between question and answer
        message_pair_container.getChildren().addAll(question_box, answer_box);

        // Add message pair container to chatPane
        chat_panel.getChildren().add(message_pair_container);

        // Add spacing at the end of each message pair
        final Region spacing = new Region();
        spacing.setMinHeight(10);

        chat_panel.getChildren().add(spacing);
    }

    public void sendButtonAction(ActionEvent actionEvent) {
        if (message_box.getText().isBlank())
            return;

        final String complete_ai_response = "Ai Response: " + AIManager.ask(message_box.getText());

        // Combine both and display.
        addQuestionAndAnswer(message_box.getText(), complete_ai_response);

        // Add conversation to DB
        conversation_manager.insertMessageToChat(current_conversation, message_box.getText(), false);
        conversation_manager.insertMessageToChat(current_conversation, complete_ai_response, true);

        // Empty current chatbox field
        message_box.setText("");
    }

    public void handleExitButtonClick(MouseEvent mouseEvent) {
        Stage window = (Stage) conversation_list_container.getScene().getWindow();
        window.close();

        AI42Main.database.storeInFile();
    }

    public void handleSettingsButton(MouseEvent mouseEvent) {
    }

    public void handleLogoutButton(MouseEvent mouseEvent) {
        SceneManager.getInstance().loadScene("login-and-register.fxml");
    }
}