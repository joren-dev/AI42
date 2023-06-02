package nl.ai42.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.ai42.AI42Main;
import nl.ai42.managers.SceneManager;
import nl.ai42.utils.Row;
import nl.ai42.utils.ValidationUtils;

import java.io.IOException;
import java.util.HashMap;

public class LoginAndRegisterController {

    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    // Import the application's controls
    @FXML
    private Label invalid_login_credentials;
    @FXML
    private Label invalid_signup_credentials;
    @FXML
    private Button exit_button;
    @FXML
    private TextField login_username_text_field;
    @FXML
    private TextField login_password_password_field;
    @FXML
    private TextField sign_up_username_text_field;
    @FXML
    private TextField sign_up_email_text_field;
    @FXML
    private TextField sign_up_password_password_field;
    @FXML
    private TextField sign_up_repeat_password_password_field;
    @FXML
    private DatePicker sign_up_date_date_picker;
    @FXML
    private CheckBox termsConditionsCheckbox;

    // Creation of methods which are activated on events in the forms
    @FXML
    protected void onExitButton() {
        Stage stage = (Stage) exit_button.getScene().getWindow();
        stage.close();
    }

    protected void resetFields() {
        termsConditionsCheckbox.setStyle(successStyle);
        sign_up_email_text_field.setStyle(successStyle);
        sign_up_password_password_field.setStyle(successStyle);
        sign_up_repeat_password_password_field.setStyle(successStyle);
        sign_up_username_text_field.setStyle(successStyle);
        invalid_login_credentials.setText("");
        login_username_text_field.setStyle(successStyle);
        login_password_password_field.setStyle(successStyle);
        sign_up_date_date_picker.setStyle(successStyle);
    }
    @FXML
    protected void onLoginButtonClick() {
        this.resetFields();

        if (login_username_text_field.getText().isBlank() || login_password_password_field.getText().isBlank()) {
            invalid_login_credentials.setText("Not all required fields are filled in.");
            invalid_signup_credentials.setText("");

            if (login_username_text_field.getText().isBlank()) 
                login_username_text_field.setStyle(errorStyle);
                
            if (login_password_password_field.getText().isBlank())
                login_password_password_field.setStyle(errorStyle);
        } else {
            if (AI42Main.database.getTable("user").select((row) -> row.getValue("username").equals(login_username_text_field.getText()) && row.getValue("password").equals(login_password_password_field.getText())).size() == 1) {
                invalid_login_credentials.setText("Login Successful!");
                invalid_login_credentials.setStyle(successMessage);
                login_username_text_field.setStyle(successStyle);
                login_password_password_field.setStyle(successStyle);

                // Change views to chat-view.fxml
                SceneManager.getInstance().loadScene("chat-view.fxml");
            } else {
                invalid_login_credentials.setText("Username or password is invalid.");
                invalid_login_credentials.setStyle(errorMessage);
                login_username_text_field.setStyle(errorStyle);
                login_password_password_field.setStyle(errorStyle);
            }
            invalid_signup_credentials.setText("");
        }
    }

    @FXML
    protected void onSignUpButtonClick() {
        this.resetFields();

        boolean error = false;

        if (!termsConditionsCheckbox.isSelected()) {
            invalid_signup_credentials.setStyle(errorStyle);
            invalid_signup_credentials.setText("Please accept the terms.");
            termsConditionsCheckbox.setStyle(errorStyle);
            error = true;
        }

        if (sign_up_username_text_field.getText().isBlank() || sign_up_email_text_field.getText().isBlank() ||
                sign_up_password_password_field.getText().isBlank() || sign_up_repeat_password_password_field.getText().isBlank()
        ) {
            error = true;
            invalid_signup_credentials.setText("Not all required fields are filled in.");

            if (sign_up_username_text_field.getText().isBlank())
                sign_up_username_text_field.setStyle(errorStyle);

            if (sign_up_email_text_field.getText().isBlank())
                sign_up_email_text_field.setStyle(errorStyle);

            if (sign_up_password_password_field.getText().isBlank())
                sign_up_password_password_field.setStyle(errorStyle);
            
            if (sign_up_repeat_password_password_field.getText().isBlank())
                sign_up_repeat_password_password_field.setStyle(errorStyle);
        }

        if (!ValidationUtils.is_valid_password(sign_up_password_password_field.getText())) {
            error = true;
            invalid_signup_credentials.setText("Password does not satisfy requirements.");
            sign_up_password_password_field.setStyle(errorStyle);
        }

        if (!ValidationUtils.is_valid_email(sign_up_email_text_field.getText())) {
            error = true;
            invalid_signup_credentials.setText("Email does not satisfy requirements.");
            sign_up_email_text_field.setStyle(errorStyle);
        }

        if (!ValidationUtils.is_valid_name(sign_up_username_text_field.getText())) {
            error = true;
            invalid_signup_credentials.setText("Username does not satisfy requirements.");
            sign_up_username_text_field.setStyle(errorStyle);
        }

        if (sign_up_date_date_picker.getValue() == null) {
            error = true;
            invalid_signup_credentials.setText("Not all required fields are filled in.");
            sign_up_date_date_picker.setStyle(errorStyle);
        }

        if (error)
            return;

        if (AI42Main.database.getTable("user").select((row) -> row.getValue("username").equals(sign_up_username_text_field.getText())).size() == 1) {
            invalid_signup_credentials.setText("Username already registered.");
            invalid_signup_credentials.setStyle(errorStyle);
            sign_up_username_text_field.setStyle(errorStyle);
            return;
        }

        if (AI42Main.database.getTable("user").select((row) -> row.getValue("email").equals(sign_up_email_text_field.getText())).size() == 1) {
            invalid_signup_credentials.setText("Email already registered.");
            invalid_signup_credentials.setStyle(errorStyle);
            sign_up_email_text_field.setStyle(errorStyle);
            return;
        }

        if (!sign_up_repeat_password_password_field.getText().equals(sign_up_password_password_field.getText())) {
            invalid_signup_credentials.setText("The Passwords don't match!");
            sign_up_password_password_field.setStyle(errorStyle);
            sign_up_repeat_password_password_field.setStyle(errorStyle);
            return; // Return early if passwords don't match
        }

        invalid_signup_credentials.setText("You are set!");
        invalid_signup_credentials.setStyle(successMessage);
        HashMap<String, String> data = new HashMap<>();
        data.put("username", sign_up_username_text_field.getText());
        data.put("email", sign_up_email_text_field.getText());
        data.put("password", sign_up_password_password_field.getText());
        data.put("date_of_birth", sign_up_date_date_picker.getValue().toString());
        AI42Main.database.getTable("user").insert(new Row(data));

        try {
            AI42Main.database.storeInFile("AI42.db");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
