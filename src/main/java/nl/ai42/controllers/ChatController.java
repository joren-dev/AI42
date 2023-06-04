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
import nl.ai42.managers.SceneManager;
import nl.ai42.utils.Row;

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
    public void initialize() {
        final int conversationCount = AI42Main.database.getTable("chat").select(
                (row) -> row.getValue("username").equals(AI42Main.currentUser)
        ).size();

        // Create the amount of conversations based on the user's conversations
        for (int i = 0; i < conversationCount; i++)
            this.startConversation(new ActionEvent(), false);
    }

    @FXML
    public void startConversation(ActionEvent actionEvent) {
        startConversation(actionEvent, true);
    }
    @FXML
    public void startConversation(ActionEvent action_event, boolean create)
    {
        // Create a new conversation button
        final Button new_conversation_button = new Button("Conversation " + conversation_count);

        if (create) {
            AI42Main.database.getTable("chat").insert(new Row(new HashMap<>() {{
                put("username", AI42Main.currentUser);
                put("chatname", "Conversation " + conversation_count);
            }}));
        }

        AI42Main.database.storeInFile();

        current_conversation = "Conversation " + conversation_count;
        new_conversation_button.setPrefWidth(300);
        new_conversation_button.setOnAction(this::openConversation);

        // Apply the same style as the startConversationButton
        new_conversation_button.setFont(start_conversation_button.getFont());

        // Set the spacing between conversations
        conversation_list_container.setSpacing(10);

        // Add the new conversation button at the bottom
        conversation_list_container.getChildren().add(new_conversation_button);

        conversation_count++;
    }

    private void openConversation(ActionEvent action_event) {
        // Retrieve the clicked conversation button
        final Button conversationButton = (Button) action_event.getSource();

        // Get the conversation name from the button's text
        final String conversation_name = conversationButton.getText();

        // Perform actions to open the conversation and start chatting
        System.out.println("Opening conversation: " + conversation_name);
        current_conversation = conversation_name;

        chat_panel.getChildren().clear();
        ArrayList<Row> rows = AI42Main.database.getTable("chatmsg").select((row) ->
                row.getValue("username").equals(AI42Main.currentUser) && row.getValue("chatname").equals(
                        current_conversation)
        );

        final HBox question_box = new HBox();
        question_box.getStyleClass().add("question");
        question_box.setPadding(new Insets(10, 0, 5, 0)); // Add padding between question and answer

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
        if (message_box.getText().isEmpty())
            return;
        String ai_response = AIManager.ask(message_box.getText());
        addQuestionAndAnswer(message_box.getText(), ai_response);

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
            put("msg_content", ai_response);
            put("is_ai", "true");
            put("sent", new Date().toString());
        }}));

        AI42Main.database.storeInFile();
        message_box.setText("");
    }
    public void updateDisplayedMessages()
    {

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