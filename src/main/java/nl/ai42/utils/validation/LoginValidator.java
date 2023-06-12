package nl.ai42.utils.validation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nl.ai42.AI42Main;
import nl.ai42.managers.SceneManager;
import nl.ai42.utils.validation.utility.StatusUtility;
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

        if (StatusUtility.isEmptyTextField(usernameTextField) || StatusUtility.isEmptyTextField(passwordField)) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Not all required fields are filled in.");
            handleEmptyFields();
            error = true;
        }

        return !error;
    }

    private void handleEmptyFields() {
        if (StatusUtility.isEmptyTextField(usernameTextField))
            StatusUtility.setErrorStyle(usernameTextField);

        if (StatusUtility.isEmptyTextField(passwordField))
            StatusUtility.setErrorStyle(passwordField);
    }

    private boolean checkCredentials() throws NoSuchAlgorithmException {
        String username = usernameTextField.getText();
        String password = Sha3Hash.calculateSHA3Hash(passwordField.getText());

        if (AI42Main.database.getTable("user").select(row ->
                row.getValue("username").equals(username) && row.getValue("password").equals(password)).size() == 1) {
            return true;
        } else {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Username or password is invalid.");
            StatusUtility.setErrorStyle(usernameTextField);
            StatusUtility.setErrorStyle(passwordField);
            return false;
        }
    }

    private void loginUser() {
        StatusUtility.setSuccessMessage(invalidCredentialsLabel, "Login Successful!");
        AI42Main.currentUser = usernameTextField.getText();
        SceneManager.getInstance().loadScene("chat-view.fxml");
    }

    private void resetFields() {
        StatusUtility.clearErrorMessage(invalidCredentialsLabel);
        StatusUtility.clearErrorStyle(usernameTextField);
        StatusUtility.clearErrorStyle(passwordField);
    }
}
