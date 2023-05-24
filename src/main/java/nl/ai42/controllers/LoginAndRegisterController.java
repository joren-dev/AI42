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
    private Label invalidLoginCredentials;
    @FXML
    private Label invalidSignupCredentials;
    @FXML
    private Button exitButton;
    @FXML
    private TextField loginUsernameTextField;
    @FXML
    private TextField loginPasswordPasswordField;
    @FXML
    private TextField signUpUsernameTextField;
    @FXML
    private TextField signUpEmailTextField;
    @FXML
    private TextField signUpPasswordPasswordField;
    @FXML
    private TextField signUpRepeatPasswordPasswordField;
    @FXML
    private DatePicker signUpDateDatePicker;

    // Creation of methods which are activated on events in the forms
    @FXML
    protected void onExitButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onLoginButtonClick() {
//        invalidSignupCredentials.setStyle(successStyle);
        signUpEmailTextField.setStyle(successStyle);
        signUpPasswordPasswordField.setStyle(successStyle);
        signUpRepeatPasswordPasswordField.setStyle(successStyle);
        signUpUsernameTextField.setStyle(successStyle);
        invalidLoginCredentials.setText("");
        invalidLoginCredentials.setStyle(successMessage);
        loginUsernameTextField.setStyle(successStyle);
        loginPasswordPasswordField.setStyle(successStyle);
        signUpDateDatePicker.setStyle(successStyle);

        if (loginUsernameTextField.getText().isBlank() || loginPasswordPasswordField.getText().isBlank()) {
            invalidLoginCredentials.setText("Not all required fields are filled in.");
            invalidLoginCredentials.setStyle(errorMessage);
            invalidSignupCredentials.setText("");

            if (loginUsernameTextField.getText().isBlank()) {
                loginUsernameTextField.setStyle(errorStyle);
            }
            if (loginPasswordPasswordField.getText().isBlank()) {
                loginPasswordPasswordField.setStyle(errorStyle);
            }
        } else {
            invalidLoginCredentials.setText("Login Successful!");
            invalidLoginCredentials.setStyle(successMessage);
            loginUsernameTextField.setStyle(successStyle);
            loginPasswordPasswordField.setStyle(successStyle);
            invalidSignupCredentials.setText("");
        }
    }

    @FXML
    protected void onSignUpButtonClick() {

        boolean error = false;

        invalidSignupCredentials.setStyle(successStyle);
        signUpEmailTextField.setStyle(successStyle);
        signUpPasswordPasswordField.setStyle(successStyle);
        signUpRepeatPasswordPasswordField.setStyle(successStyle);
        signUpUsernameTextField.setStyle(successStyle);
        invalidLoginCredentials.setText("");
        invalidLoginCredentials.setStyle(successMessage);
        loginUsernameTextField.setStyle(successStyle);
        loginPasswordPasswordField.setStyle(successStyle);
        signUpDateDatePicker.setStyle(successStyle);

        if (signUpUsernameTextField.getText().isBlank() || signUpEmailTextField.getText().isBlank() ||
                signUpPasswordPasswordField.getText().isBlank() || signUpRepeatPasswordPasswordField.getText().isBlank()
        ) {
            error = true;
            invalidSignupCredentials.setText("Not all required fields are filled in.");
            invalidSignupCredentials.setStyle(errorMessage);

            if (signUpUsernameTextField.getText().isBlank()) signUpUsernameTextField.setStyle(errorStyle);
            if (signUpEmailTextField.getText().isBlank()) signUpEmailTextField.setStyle(errorStyle);
            if (signUpPasswordPasswordField.getText().isBlank()) signUpPasswordPasswordField.setStyle(errorStyle);
            if (signUpRepeatPasswordPasswordField.getText().isBlank()) signUpRepeatPasswordPasswordField.setStyle(errorStyle);
        }

        if (!ValidationUtils.is_valid_password(signUpPasswordPasswordField.getText())) {
            error = true;
            invalidSignupCredentials.setText("Password does not satisfy requirements.");
            signUpPasswordPasswordField.setStyle(errorStyle);
        }

        if (!ValidationUtils.is_valid_email(signUpEmailTextField.getText())) {
            error = true;
            invalidSignupCredentials.setText("Email does not satisfy requirements.");
            signUpEmailTextField.setStyle(errorStyle);
        }

        if (!ValidationUtils.is_valid_name(signUpUsernameTextField.getText())) {
            error = true;
            invalidSignupCredentials.setText("Username does not satisfy requirements.");
            signUpUsernameTextField.setStyle(errorStyle);
        }

        if (signUpDateDatePicker.getValue() == null) {
            error = true;
            invalidSignupCredentials.setText("Not all required fields are filled in.");
            signUpDateDatePicker.setStyle(errorStyle);
        }

        if (error) return;

        if (signUpRepeatPasswordPasswordField.getText().equals(signUpPasswordPasswordField.getText())) {
            invalidSignupCredentials.setText("You are set!");
        } else {
            invalidSignupCredentials.setText("The Passwords don't match!");
//            invalidSignupCredentials.setStyle(errorMessage);
            signUpPasswordPasswordField.setStyle(errorStyle);
            signUpRepeatPasswordPasswordField.setStyle(errorStyle);
        }
    }
}
