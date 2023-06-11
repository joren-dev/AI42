package nl.ai42.utils.datastructs;

import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpData {
    private final TextField usernameTextField;
    private final TextField emailTextField;
    private final TextField passwordField;
    private final TextField repeatPasswordField;
    private final CheckBox termsConditionsCheckbox;
    private final Label invalidCredentialsLabel;
    private final DatePicker datePicker;

    public SignUpData(TextField usernameTextField, TextField emailTextField, TextField passwordField,
                      TextField repeatPasswordField, CheckBox termsConditionsCheckbox, Label invalidCredentialsLabel,
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
