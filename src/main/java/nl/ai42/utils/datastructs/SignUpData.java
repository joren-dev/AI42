package nl.ai42.utils.datastructs;

import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpData {
    private TextField usernameTextField;
    private TextField emailTextField;
    private TextField passwordField;
    private TextField repeatPasswordField;
    private CheckBox termsConditionsCheckbox;
    private Label invalidCredentialsLabel;
    private DatePicker datePicker;

    public SignUpData(TextField usernameTextField, TextField emailTextField,
                      TextField passwordField, TextField repeatPasswordField,
                      CheckBox termsConditionsCheckbox, Label invalidCredentialsLabel,
                      DatePicker datePicker) {
        this.usernameTextField = usernameTextField;
        this.emailTextField = emailTextField;
        this.passwordField = passwordField;
        this.repeatPasswordField = repeatPasswordField;
        this.termsConditionsCheckbox = termsConditionsCheckbox;
        this.invalidCredentialsLabel = invalidCredentialsLabel;
        this.datePicker = datePicker;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextField getRepeatPasswordField() {
        return repeatPasswordField;
    }

    public CheckBox getTermsConditionsCheckbox() {
        return termsConditionsCheckbox;
    }

    public Label getInvalidCredentialsLabel() {
        return invalidCredentialsLabel;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
}
