package nl.ai42.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.ai42.AI42Main;
import nl.ai42.managers.SceneManager;
import nl.ai42.utils.validation.SignUpValidator;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
    protected void onExitButton()
    {
        AI42Main.database.storeInFile();

        Stage stage = (Stage) exit_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onTermsAndConditions() {
        // Replace the URL below with the actual link you want to open
        String url = "https://www.example.com/terms";

        // Open the link in the default browser
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
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
                AI42Main.currentUser = login_username_text_field.getText();
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
        SignUpValidator signUpValidator = new SignUpValidator(sign_up_username_text_field, sign_up_email_text_field,
                sign_up_password_password_field, sign_up_repeat_password_password_field, termsConditionsCheckbox,
                invalid_signup_credentials, sign_up_date_date_picker);

        signUpValidator.validateAndRegister();
    }

}
