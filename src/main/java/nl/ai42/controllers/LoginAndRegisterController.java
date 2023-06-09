package nl.ai42.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.ai42.AI42Main;

import nl.ai42.utils.validation.SignInValidator;
import nl.ai42.utils.validation.SignUpValidator;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

public class LoginAndRegisterController {

    // Strings which hold css elements to easily re-use in the application
    protected String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

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
    protected void onLoginButtonClick() throws NoSuchAlgorithmException {
        resetFields();

        SignInValidator signInValidator = new SignInValidator(
                login_username_text_field, login_password_password_field, invalid_login_credentials
        );
        signInValidator.validateAndLogin();

        invalid_signup_credentials.setText("");
    }

    @FXML
    protected void onSignUpButtonClick() throws NoSuchAlgorithmException {
        SignUpValidator signUpValidator = new SignUpValidator();

        signUpValidator.setCredentialFields(sign_up_username_text_field, sign_up_email_text_field,
                sign_up_password_password_field, sign_up_repeat_password_password_field);
        signUpValidator.setAdditionalFields(termsConditionsCheckbox, invalid_signup_credentials, sign_up_date_date_picker);

        signUpValidator.validateAndRegister();
    }

}
