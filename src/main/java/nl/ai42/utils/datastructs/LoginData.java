package nl.ai42.utils.datastructs;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginData {
    private final TextField usernameTextField;
    private final TextField passwordField;
    private final Label invalidCredentialsLabel;

    public LoginData(TextField usernameTextField, TextField passwordField, Label invalidCredentialsLabel) {
        this.usernameTextField = usernameTextField;
        this.passwordField = passwordField;
        this.invalidCredentialsLabel = invalidCredentialsLabel;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Label getInvalidCredentialsLabel() {
        return invalidCredentialsLabel;
    }
}
