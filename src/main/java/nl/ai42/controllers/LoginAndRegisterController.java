package nl.ai42.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.ai42.utils.ValidationUtils;

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

    // Creation of methods which are activated on events in the forms
    @FXML
    protected void onExitButton() {
        Stage stage = (Stage) exit_button.getScene().getWindow();
        stage.close();
    }

    protected void resetFields() {
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
            invalid_login_credentials.setText("Login Successful!");
            invalid_login_credentials.setStyle(successMessage);
            login_username_text_field.setStyle(successStyle);
            login_password_password_field.setStyle(successStyle);
            invalid_signup_credentials.setText("");
        }
    }

    @FXML
    protected void onSignUpButtonClick() {
        this.resetFields();

        boolean error = false;

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

        if (sign_up_repeat_password_password_field.getText().equals(sign_up_password_password_field.getText())) {
            invalid_signup_credentials.setText("You are set!");
            invalid_signup_credentials.setStyle(successMessage);
        } else {
            invalid_signup_credentials.setText("The Passwords don't match!");
            sign_up_password_password_field.setStyle(errorStyle);
            sign_up_repeat_password_password_field.setStyle(errorStyle);
        }
    }
}
