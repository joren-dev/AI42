package nl.ai42.utils.validation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.util.Duration;
import nl.ai42.AI42Main;
import nl.ai42.utils.database.Row;

import java.util.HashMap;

public class SignUpValidator {
    private final TextField usernameTextField;
    private final TextField emailTextField;
    private final TextField passwordField;
    private final TextField repeatPasswordField;
    private final CheckBox termsConditionsCheckbox;
    private final Label invalidCredentialsLabel;
    private final DatePicker datePicker;

    public SignUpValidator(TextField usernameTextField, TextField emailTextField, TextField passwordField,
                           TextField repeatPasswordField, CheckBox termsConditionsCheckbox,
                           Label invalidCredentialsLabel, DatePicker datePicker) {
        this.usernameTextField = usernameTextField;
        this.emailTextField = emailTextField;
        this.passwordField = passwordField;
        this.repeatPasswordField = repeatPasswordField;
        this.termsConditionsCheckbox = termsConditionsCheckbox;
        this.invalidCredentialsLabel = invalidCredentialsLabel;
        this.datePicker = datePicker;
    }

    public void validateAndRegister() {
        resetErrorFields();
        if (!validateSignUpForm())
            return;

        if (checkUniqueCredentials())
            registerUser();
    }

    private boolean validateSignUpForm() {
        if (!termsConditionsCheckbox.isSelected()) {
            setErrorMessage("Please accept the terms.");
            setErrorStyle(termsConditionsCheckbox);
            return false;
        }

        if (isEmptyField(usernameTextField) || isEmptyField(emailTextField) ||
                isEmptyField(passwordField) || isEmptyField(repeatPasswordField) ||
                datePicker.getValue() == null) {
            setErrorMessage("Not all required fields are filled in.");
            handleEmptyFields();
            return false;
        }

        if (!ValidationUtils.is_valid_name(usernameTextField.getText())) {
            setErrorMessage("Username does not satisfy requirements.");
            setErrorStyle(usernameTextField);
            return false;
        }

        if (!ValidationUtils.is_valid_email(emailTextField.getText())) {
            setErrorMessage("Email does not satisfy requirements.");
            setErrorStyle(emailTextField);
            return false;
        }

        if (!ValidationUtils.is_valid_password(passwordField.getText())) {
            setErrorMessage("Password does not satisfy requirements.");
            setErrorStyle(passwordField);
            return false;
        }

        if (datePicker.getValue() != null) {
            if (!ValidationUtils.is_valid_date(datePicker.getValue().toString())) {
                setErrorMessage("Please select a valid date of birth.");
                setErrorStyle(datePicker);
                return false;
            }
        }

        return true;
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

        if (datePicker.getValue() == null)
            setErrorStyle(datePicker);
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
            setErrorMessage("The passwords don't match!");
            setErrorStyle(passwordField);
            setErrorStyle(repeatPasswordField);
            return false;
        } else if (datePicker.getValue() == null) {
            setErrorMessage("Please select a date of birth.");
            setErrorStyle(datePicker);
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
        setSuccessMessage("Registration successful!");
        HashMap<String, String> data = new HashMap<>();
        data.put("username", usernameTextField.getText());
        data.put("email", emailTextField.getText());
        data.put("password", passwordField.getText());
        data.put("date_of_birth", datePicker.getValue().toString());
        AI42Main.database.getTable("user").insert(new Row(data));
        AI42Main.database.storeInFile();

        resetFields();
    }

    private void resetFields() {
        // Delay in milliseconds before resetting the fields
        final int delay = 2000;

        // Create a timeline with the specified delay
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(delay), event -> {
            // Reset the fields here
            usernameTextField.setText("");
            emailTextField.setText("");
            passwordField.setText("");
            repeatPasswordField.setText("");
            termsConditionsCheckbox.setSelected(false);
            datePicker.setValue(null);
        }));

        // Start the timeline
        timeline.play();
    }

    private void resetErrorFields() {
        clearErrorMessage();
        clearErrorStyle(usernameTextField);
        clearErrorStyle(emailTextField);
        clearErrorStyle(passwordField);
        clearErrorStyle(repeatPasswordField);
        clearErrorStyle(datePicker);
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
