package nl.ai42.utils.validation;

import javafx.scene.control.*;
import nl.ai42.utils.Row;
import javafx.scene.control.TextField;

import java.util.HashMap;
import nl.ai42.AI42Main;

public class SignUpValidator {
    private final TextField usernameTextField;
    private final TextField emailTextField;
    private final TextField passwordField;
    private final TextField repeatPasswordField;
    private final CheckBox termsConditionsCheckbox;
    private final Label invalidCredentialsLabel;
    private final DatePicker datePicker;

    public SignUpValidator(TextField usernameTextField, TextField emailTextField,
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

    public void validateAndRegister() {
        resetFields();
        if (!validateSignUpForm())
            return;

        if (checkUniqueCredentials())
            registerUser();
    }
    private boolean validateSignUpForm() {
        boolean error = false;

        if (!termsConditionsCheckbox.isSelected()) {
            setErrorMessage("Please accept the terms.");
            setErrorStyle(termsConditionsCheckbox);
            error = true;
        }

        if (isEmptyField(usernameTextField) || isEmptyField(emailTextField) ||
                isEmptyField(passwordField) || isEmptyField(repeatPasswordField)) {
            setErrorMessage("Not all required fields are filled in.");
            handleEmptyFields();
            error = true;
        }

        if (!ValidationUtils.is_valid_password(passwordField.getText())) {
            setErrorMessage("Password does not satisfy requirements.");
            setErrorStyle(passwordField);
            error = true;
        }

        if (!ValidationUtils.is_valid_email(emailTextField.getText())) {
            setErrorMessage("Email does not satisfy requirements.");
            setErrorStyle(emailTextField);
            error = true;
        }

        if (!ValidationUtils.is_valid_name(usernameTextField.getText())) {
            setErrorMessage("Username does not satisfy requirements.");
            setErrorStyle(usernameTextField);
            error = true;
        }

        if (datePicker.getValue() == null) {
            setErrorMessage("Not all required fields are filled in.");
            setErrorStyle(datePicker);
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

        if (isEmptyField(emailTextField))
            setErrorStyle(emailTextField);

        if (isEmptyField(passwordField))
            setErrorStyle(passwordField);

        if (isEmptyField(repeatPasswordField))
            setErrorStyle(repeatPasswordField);
    }

    private boolean checkUniqueCredentials() {
        if (isUsernameRegistered()) {
            setErrorMessage("Username already registered.");
            setErrorStyle(usernameTextField);
            return false;
        } else if (isEmailRegistered()) {
            setErrorMessage("Email already registered.");
            setErrorStyle(emailTextField);
            return false;
        } else if (!repeatPasswordField.getText().equals(passwordField.getText())) {
            setErrorMessage("The Passwords don't match!");
            setErrorStyle(passwordField);
            setErrorStyle(repeatPasswordField);
            return false;
        }
        return true;
    }

    private boolean isUsernameRegistered() {
        return AI42Main.database.getTable("user").select(row ->
                row.getValue("username").equals(usernameTextField.getText())).size() == 1;
    }

    private boolean isEmailRegistered() {
        return AI42Main.database.getTable("user").select(row ->
                row.getValue("email").equals(emailTextField.getText())).size() == 1;
    }

    private void registerUser() {
        setSuccessMessage("You are set!");
        HashMap<String, String> data = new HashMap<>();
        data.put("username", usernameTextField.getText());
        data.put("email", emailTextField.getText());
        data.put("password", passwordField.getText());
        data.put("date_of_birth", datePicker.getValue().toString());
        AI42Main.database.getTable("user").insert(new Row(data));
        AI42Main.database.storeInFile();
    }

    private void resetFields() {
        clearErrorMessage();
        clearErrorStyle(usernameTextField);
        clearErrorStyle(emailTextField);
        clearErrorStyle(passwordField);
        clearErrorStyle(repeatPasswordField);
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

    private void setErrorStyle(Control control) {
        control.setStyle("-fx-border-color: red;");
    }

    private void clearErrorStyle(Control control) {
        control.setStyle(null);
    }
}
