package nl.ai42.utils.validation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.util.Duration;
import nl.ai42.AI42Main;
import nl.ai42.utils.database.Row;
import nl.ai42.utils.security.Sha3Hash;
import nl.ai42.utils.validation.utility.StatusUtility;
import nl.ai42.utils.validation.utility.ValidationUtility;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SignUpValidator {
    private TextField usernameTextField;
    private TextField emailTextField;
    private TextField passwordField;
    private TextField repeatPasswordField;
    private CheckBox termsConditionsCheckbox;
    private Label invalidCredentialsLabel;
    private DatePicker datePicker;

    public void set_credential_fields(TextField usernameTextField, TextField emailTextField, TextField passwordField,
                                    TextField repeatPasswordField) {
        this.usernameTextField = usernameTextField;
        this.emailTextField = emailTextField;
        this.passwordField = passwordField;
        this.repeatPasswordField = repeatPasswordField;
    }

    public void set_additional_fields(CheckBox termsConditionsCheckbox,
                                    Label invalidCredentialsLabel, DatePicker datePicker) {
        this.termsConditionsCheckbox = termsConditionsCheckbox;
        this.invalidCredentialsLabel = invalidCredentialsLabel;
        this.datePicker = datePicker;
    }

    public void validateAndRegister() throws NoSuchAlgorithmException {
        resetErrorFields();
        if (!validateSignUpForm())
            return;

        if (checkUniqueCredentials())
            registerUser();
    }

    private boolean validateSignUpForm() {
        if (!ValidationUtility.validateRequiredFields(usernameTextField, emailTextField, passwordField,
                repeatPasswordField, datePicker)) {
            handleEmptyFields();
            return false;
        }

        if (!ValidationUtility.validateUsername(usernameTextField)) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Username does not satisfy requirements.");
            StatusUtility.setErrorStyle(usernameTextField);
            return false;
        }

        if (!ValidationUtility.validateEmail(emailTextField)) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Email does not satisfy requirements.");
            StatusUtility.setErrorStyle(emailTextField);
            return false;
        }

        if (!ValidationUtility.validatePassword(passwordField)) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Password does not satisfy requirements.");
            StatusUtility.setErrorStyle(passwordField);
            return false;
        }

        if (!ValidationUtility.validateDateOfBirth(datePicker)) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Please select a valid date of birth.");
            StatusUtility.setErrorStyle(datePicker);
            return false;
        }

        if (!ValidationUtility.validateTermsConditions(termsConditionsCheckbox)) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Please accept the terms.");
            StatusUtility.setErrorStyle(termsConditionsCheckbox);
            return false;
        }

        return true;
    }

    private void handleEmptyFields() {
        StatusUtility.setErrorMessage(invalidCredentialsLabel, "Please fill in all the required fields.");

        if (StatusUtility.isEmptyTextField(usernameTextField))
            StatusUtility.setErrorStyle(usernameTextField);

        if (StatusUtility.isEmptyTextField(emailTextField))
            StatusUtility.setErrorStyle(emailTextField);

        if (StatusUtility.isEmptyTextField(passwordField))
            StatusUtility.setErrorStyle(passwordField);

        if (StatusUtility.isEmptyTextField(repeatPasswordField))
            StatusUtility.setErrorStyle(repeatPasswordField);

        if (StatusUtility.isEmptyDatePickerField(datePicker))
            StatusUtility.setErrorStyle(datePicker);
    }

    private boolean checkUniqueCredentials() {
        if (isUsernameRegistered()) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Username already registered.");
            StatusUtility.setErrorStyle(usernameTextField);
            return false;
        } else if (isEmailRegistered()) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Email already registered.");
            StatusUtility.setErrorStyle(emailTextField);
            return false;
        } else if (!repeatPasswordField.getText().equals(passwordField.getText())) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "The passwords don't match!");
            StatusUtility.setErrorStyle(passwordField);
            StatusUtility.setErrorStyle(repeatPasswordField);
            return false;
        } else if (datePicker.getValue() == null) {
            StatusUtility.setErrorMessage(invalidCredentialsLabel, "Please select a date of birth.");
            StatusUtility.setErrorStyle(datePicker);
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

    private void registerUser() throws NoSuchAlgorithmException {
        StatusUtility.setSuccessMessage(invalidCredentialsLabel, "Registration successful!");
        HashMap<String, String> data = new HashMap<>();

        data.put("username", usernameTextField.getText());
        data.put("email", emailTextField.getText());
        data.put("password", Sha3Hash.calculateSHA3Hash(passwordField.getText()));
        data.put("date_of_birth", datePicker.getValue().toString());

        AI42Main.database.getTable("user").insert(new Row(data));
        AI42Main.database.storeInFile();

        resetFields();
    }

    private void resetFields() {
        final int delay = 2000;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(delay), event -> {
            usernameTextField.setText("");
            emailTextField.setText("");
            passwordField.setText("");
            repeatPasswordField.setText("");
            termsConditionsCheckbox.setSelected(false);
            datePicker.setValue(null);
        }));
        timeline.play();
    }

    private void resetErrorFields() {
        StatusUtility.clearErrorMessage(invalidCredentialsLabel);
        StatusUtility.clearErrorStyle(usernameTextField);
        StatusUtility.clearErrorStyle(emailTextField);
        StatusUtility.clearErrorStyle(passwordField);
        StatusUtility.clearErrorStyle(repeatPasswordField);
        StatusUtility.clearErrorStyle(datePicker);
    }
}
