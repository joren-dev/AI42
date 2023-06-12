package nl.ai42.utils.validation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.util.Duration;
import nl.ai42.AI42Main;
import nl.ai42.utils.database.Row;
import nl.ai42.utils.security.Sha3Hash;
import nl.ai42.utils.validation.utility.ValidationUtility;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SignUpValidator implements ValidatorInterface {
    private TextField usernameTextField;
    private TextField emailTextField;
    private TextField passwordField;
    private TextField repeatPasswordField;
    private CheckBox termsConditionsCheckbox;
    private Label invalidCredentialsLabel;
    private DatePicker datePicker;

    public void setCredentialFields(TextField usernameTextField, TextField emailTextField, TextField passwordField,
                                    TextField repeatPasswordField) {
        this.usernameTextField = usernameTextField;
        this.emailTextField = emailTextField;
        this.passwordField = passwordField;
        this.repeatPasswordField = repeatPasswordField;
    }

    public void setAdditionalFields(CheckBox termsConditionsCheckbox,
                                    Label invalidCredentialsLabel, DatePicker datePicker) {
        this.termsConditionsCheckbox = termsConditionsCheckbox;
        this.invalidCredentialsLabel = invalidCredentialsLabel;
        this.datePicker = datePicker;
    }

    public void validateAndRegister() throws NoSuchAlgorithmException {
        resetErrorFields();

        // Check if any fields are empty
        if (!ValidationUtility.validateRequiredFields(usernameTextField, emailTextField, passwordField,
                repeatPasswordField, datePicker)) {
            setErrorMessage(invalidCredentialsLabel, "Please fill in all the required fields.");
            handleEmptyFields();
            return;
        }

        // Validate valid the filled fields
        if (!validateSignUpForm())
            return;

        // Check if the terms are agreed on
        if (!ValidationUtility.validateTermsConditions(termsConditionsCheckbox)) {
            setErrorMessage(invalidCredentialsLabel, "Please accept the terms.");
            setErrorStyle(termsConditionsCheckbox);
            return;
        }

        // Check if details are unique
        if (checkUniqueCredentials())
            registerUser();
    }

    private boolean validateSignUpForm() {
        if (!ValidationUtility.validateUsername(usernameTextField)) {
            setErrorMessage(invalidCredentialsLabel, "Username does not satisfy requirements.");
            setErrorStyle(usernameTextField);
            return false;
        }

        if (!ValidationUtility.validateEmail(emailTextField)) {
            setErrorMessage(invalidCredentialsLabel, "Email does not satisfy requirements.");
            setErrorStyle(emailTextField);
            return false;
        }

        if (!ValidationUtility.validatePassword(passwordField)) {
            setErrorMessage(invalidCredentialsLabel, "Password does not satisfy requirements.");
            setErrorStyle(passwordField);
            return false;
        }

        if (!ValidationUtility.validateDateOfBirth(datePicker)) {
            setErrorMessage(invalidCredentialsLabel, "Please select a valid date of birth.");
            setErrorStyle(datePicker);
            return false;
        }

        return true;
    }

    private void handleEmptyFields() {
        if (isEmptyTextField(usernameTextField))
            setErrorStyle(usernameTextField);

        if (isEmptyTextField(emailTextField))
            setErrorStyle(emailTextField);

        if (isEmptyTextField(passwordField))
            setErrorStyle(passwordField);

        if (isEmptyTextField(repeatPasswordField))
            setErrorStyle(repeatPasswordField);

        if (isEmptyDatePickerField(datePicker))
            setErrorStyle(datePicker);
    }

    private boolean checkUniqueCredentials() {
        if (isUsernameRegistered()) {
            setErrorMessage(invalidCredentialsLabel, "Username already registered.");
            setErrorStyle(usernameTextField);
            return false;
        } else if (isEmailRegistered()) {
            setErrorMessage(invalidCredentialsLabel, "Email already registered.");
            setErrorStyle(emailTextField);
            return false;
        } else if (!repeatPasswordField.getText().equals(passwordField.getText())) {
            setErrorMessage(invalidCredentialsLabel, "The passwords don't match!");
            setErrorStyle(passwordField);
            setErrorStyle(repeatPasswordField);
            return false;
        } else if (datePicker.getValue() == null) {
            setErrorMessage(invalidCredentialsLabel, "Please select a date of birth.");
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

    private void registerUser() throws NoSuchAlgorithmException {
        setSuccessMessage(invalidCredentialsLabel, "Registration successful!");
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
        clearErrorMessage(invalidCredentialsLabel);
        clearErrorStyle(usernameTextField);
        clearErrorStyle(emailTextField);
        clearErrorStyle(passwordField);
        clearErrorStyle(repeatPasswordField);
        clearErrorStyle(datePicker);
    }
}
