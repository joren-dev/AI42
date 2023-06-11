package nl.ai42.utils.validation;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nl.ai42.AI42Main;
import nl.ai42.managers.SceneManager;
import nl.ai42.utils.security.Sha3Hash;

import java.security.NoSuchAlgorithmException;


public class LoginValidator {
    private final TextField usernameTextField;
    private final TextField passwordField;
    private final Label invalidCredentialsLabel;

    public LoginValidator(TextField usernameTextField, TextField passwordField, Label invalidCredentialsLabel) {
        this.usernameTextField = usernameTextField;
        this.passwordField = passwordField;
        this.invalidCredentialsLabel = invalidCredentialsLabel;
    }

    public void validateAndLogin() throws NoSuchAlgorithmException {
        resetFields();
        if (!validateLoginForm())
            return;

        if (checkCredentials())
            loginUser();
    }

    private boolean validateLoginForm() {
        boolean error = false;

        if (isEmptyField(usernameTextField) || isEmptyField(passwordField)) {
            setErrorMessage("Not all required fields are filled in.");
            handleEmptyFields();
            error = true;
        }

        return !error;
    }

    private boolean isEmptyField(TextField textField) {
        return textField.getText().isBlank();
    }

    private void handleEmptyFields() {
        if (isEmptyField(usernameTextField))
            setErrorStyle(usernameTextField);

        if (isEmptyField(passwordField))
            setErrorStyle(passwordField);
    }

    private boolean checkCredentials() throws NoSuchAlgorithmException {
        String username = usernameTextField.getText();
        String password = Sha3Hash.calculateSHA3Hash(passwordField.getText());

        if (AI42Main.database.getTable("user").select(row ->
                row.getValue("username").equals(username) && row.getValue("password").equals(password)).size() == 1) {
            return true;
        } else {
            setErrorMessage("Username or password is invalid.");
            setErrorStyle(usernameTextField);
            setErrorStyle(passwordField);
            return false;
        }
    }

    private void loginUser() {
        setSuccessMessage("Login Successful!");
        AI42Main.currentUser = usernameTextField.getText();
        SceneManager.getInstance().loadScene("chat-view.fxml");
    }

    private void resetFields() {
        clearErrorMessage();
        clearErrorStyle(usernameTextField);
        clearErrorStyle(passwordField);
    }

    private void setErrorMessage(String message) {
        invalidCredentialsLabel.setText(message);
        invalidCredentialsLabel.setStyle("-fx-text-fill: red;");
    }

    private void setSuccessMessage(String message) {
        invalidCredentialsLabel.setText(message);
        invalidCredentialsLabel.setStyle("-fx-text-fill: green;");
    }

    private void clearErrorMessage() {
        invalidCredentialsLabel.setText("");
    }

    private void setErrorStyle(TextField textField) {
        textField.setStyle("-fx-border-color: red;");
    }

    private void setErrorStyle(PasswordField passwordField) {
        passwordField.setStyle("-fx-border-color: red;");
    }

    private void clearErrorStyle(TextField textField) {
        textField.setStyle(null);
    }

    private void clearErrorStyle(PasswordField passwordField) {
        passwordField.setStyle(null);
    }
}
