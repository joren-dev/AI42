package nl.ai42.utils.validation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nl.ai42.AI42Main;
import nl.ai42.managers.SceneManager;
import nl.ai42.utils.security.Sha3Hash;

import java.security.NoSuchAlgorithmException;

public class SignInValidator implements ValidatorInterface {
    private final TextField usernameTextField;
    private final TextField passwordField;
    private final Label invalidCredentialsLabel;

    public SignInValidator(TextField usernameTextField, TextField passwordField, Label invalidCredentialsLabel) {
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

        if (isEmptyTextField(usernameTextField) || isEmptyTextField(passwordField)) {
            setErrorMessage(invalidCredentialsLabel, "Not all required fields are filled in.");
            handleEmptyFields();
            error = true;
        }

        return !error;
    }

    private void handleEmptyFields() {
        if (isEmptyTextField(usernameTextField))
            setErrorStyle(usernameTextField);

        if (isEmptyTextField(passwordField))
            setErrorStyle(passwordField);
    }

    private boolean checkCredentials() throws NoSuchAlgorithmException {
        String username = usernameTextField.getText();
        String password = Sha3Hash.calculateSHA3Hash(passwordField.getText());

        if (AI42Main.database.getTable("user").select(row ->
                row.getValue("username").equals(username) && row.getValue("password").equals(password)).size() == 1) {
            return true;
        } else {
            setErrorMessage(invalidCredentialsLabel, "Username or password is invalid.");
            setErrorStyle(usernameTextField);
            setErrorStyle(passwordField);
            return false;
        }
    }

    private void loginUser() {
        setSuccessMessage(invalidCredentialsLabel, "Login Successful!");
        AI42Main.currentUser = usernameTextField.getText();
        SceneManager.getInstance().loadScene("chat-view.fxml");
    }

    private void resetFields() {
        clearErrorMessage(invalidCredentialsLabel);
        clearErrorStyle(usernameTextField);
        clearErrorStyle(passwordField);
    }
}
