package nl.ai42.utils.validation;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nl.ai42.AI42Main;
import nl.ai42.managers.SceneManager;
import nl.ai42.utils.datastructs.LoginData;

public class LoginValidator {
    private LoginData loginData;

    public LoginValidator(TextField usernameTextField, TextField passwordField, Label invalidCredentialsLabel) {
        this.loginData = new LoginData(usernameTextField, passwordField, invalidCredentialsLabel);
    }

    public void validateAndLogin() {
        resetFields();
        if (!validateLoginForm())
            return;

        if (checkCredentials())
            loginUser();
    }

    private boolean validateLoginForm() {
        boolean error = false;

        if (isEmptyField(loginData.getUsernameTextField()) || isEmptyField(loginData.getPasswordField())) {
            setErrorMessage("Not all required fields are filled in.");
            handleEmptyFields();
            error = true;
        }

        return !error;
    }

    private boolean isEmptyField(TextField textField) {
        return textField.getText().isBlank();
    }

    private boolean isEmptyField(PasswordField passwordField) {
        return passwordField.getText().isBlank();
    }

    private void handleEmptyFields() {
        if (isEmptyField(loginData.getUsernameTextField()))
            setErrorStyle(loginData.getUsernameTextField());

        if (isEmptyField(loginData.getPasswordField()))
            setErrorStyle(loginData.getPasswordField());
    }

    private boolean checkCredentials() {
        String username = loginData.getUsernameTextField().getText();
        String password = loginData.getPasswordField().getText();

        if (AI42Main.database.getTable("user").select(row ->
                row.getValue("username").equals(username) && row.getValue("password").equals(password)).size() == 1) {
            return true;
        } else {
            setErrorMessage("Username or password is invalid.");
            setErrorStyle(loginData.getUsernameTextField());
            setErrorStyle(loginData.getPasswordField());
            return false;
        }
    }

    private void loginUser() {
        setSuccessMessage("Login Successful!");
        AI42Main.currentUser = loginData.getUsernameTextField().getText();
        SceneManager.getInstance().loadScene("chat-view.fxml");
    }

    private void resetFields() {
        clearErrorMessage();
        clearErrorStyle(loginData.getUsernameTextField());
        clearErrorStyle(loginData.getPasswordField());
    }

    private void setErrorMessage(String message) {
        loginData.getInvalidCredentialsLabel().setText(message);
        loginData.getInvalidCredentialsLabel().setStyle("-fx-text-fill: red;");
    }

    private void setSuccessMessage(String message) {
        loginData.getInvalidCredentialsLabel().setText(message);
        loginData.getInvalidCredentialsLabel().setStyle("-fx-text-fill: green;");
    }

    private void clearErrorMessage() {
        loginData.getInvalidCredentialsLabel().setText("");
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
