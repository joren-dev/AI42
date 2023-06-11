package nl.ai42.utils.datastructs;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginData {
    private TextField usernameTextField;
    private TextField passwordField;
    private Label invalidCredentialsLabel;

    public void setUsernameTextField(TextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public void setPasswordField(TextField passwordField) {
        this.passwordField = passwordField;
    }

    public void setInvalidCredentialsLabel(Label invalidCredentialsLabel) {
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
