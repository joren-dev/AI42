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

    public void setUsernameAndEmailTextField(TextField usernameTextField, TextField emailTextField) {
        this.usernameTextField = usernameTextField;
        this.emailTextField = emailTextField;
    }

    public void setPasswordAndRepeatPasswordField(TextField passwordField, TextField repeatPasswordField) {
        this.passwordField = passwordField;
        this.repeatPasswordField = repeatPasswordField;
    }

    public void setTermsConditionsCheckboxAndInvalidCredentialsLabel(CheckBox termsConditionsCheckbox, Label invalidCredentialsLabel) {
        this.termsConditionsCheckbox = termsConditionsCheckbox;
        this.invalidCredentialsLabel = invalidCredentialsLabel;
    }

    public void setDatePicker(DatePicker datePicker) {
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
