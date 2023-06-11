package nl.ai42.utils.validation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.util.Duration;
import nl.ai42.utils.database.Row;
import javafx.scene.control.TextField;

import java.util.HashMap;
import nl.ai42.AI42Main;
import nl.ai42.utils.datastructs.SignUpData;

public class SignUpValidator {
    private final SignUpData signUpData;
    private final Label invalidCredentialsLabel;

    public SignUpValidator(SignUpData signUpData, Label invalidCredentialsLabel) {
        this.signUpData = signUpData;
        this.invalidCredentialsLabel = invalidCredentialsLabel;
    }

    public void validateAndRegister() {
        resetErrorFields();
        if (!validateSignUpForm())
            return;

        if (checkUniqueCredentials())
            registerUser();
    }

    private boolean validateSignUpForm() {
        boolean error = false;

        if (!signUpData.getTermsConditionsCheckbox().isSelected()) {
            setErrorMessage("Please accept the terms.");
            setErrorStyle(signUpData.getTermsConditionsCheckbox());
            error = true;
        }

        if (isEmptyField(signUpData.getUsernameTextField()) || isEmptyField(signUpData.getEmailTextField()) ||
                isEmptyField(signUpData.getPasswordField()) || isEmptyField(signUpData.getRepeatPasswordField())
                || signUpData.getDatePicker().getValue() == null) {
            setErrorMessage("Not all required fields are filled in.");
            handleEmptyFields();
            error = true;
        }

        if (!ValidationUtils.is_valid_password(signUpData.getPasswordField().getText())) {
            setErrorMessage("Password does not satisfy requirements.");
            setErrorStyle(signUpData.getPasswordField());
            error = true;
        }

        if (!ValidationUtils.is_valid_email(signUpData.getEmailTextField().getText())) {
            setErrorMessage("Email does not satisfy requirements.");
            setErrorStyle(signUpData.getEmailTextField());
            error = true;
        }

        if (!ValidationUtils.is_valid_name(signUpData.getUsernameTextField().getText())) {
            setErrorMessage("Username does not satisfy requirements.");
            setErrorStyle(signUpData.getUsernameTextField());
            error = true;
        }

        if (signUpData.getDatePicker().getValue() != null) {
            if (!ValidationUtils.is_valid_date(signUpData.getDatePicker().getValue().toString())) {
                setErrorMessage("Please select a valid date of birth.");
                setErrorStyle(signUpData.getDatePicker());
                error = true;
            }
        }

        return !error;
    }

    private boolean isEmptyField(TextField textField) {
        return textField.getText().isBlank();
    }

    private void handleEmptyFields() {
        if (isEmptyField(signUpData.getUsernameTextField()))
            setErrorStyle(signUpData.getUsernameTextField());

        if (isEmptyField(signUpData.getEmailTextField()))
            setErrorStyle(signUpData.getEmailTextField());

        if (isEmptyField(signUpData.getPasswordField()))
            setErrorStyle(signUpData.getPasswordField());

        if (isEmptyField(signUpData.getRepeatPasswordField()))
            setErrorStyle(signUpData.getRepeatPasswordField());

        if (signUpData.getDatePicker().getValue() == null)
            setErrorStyle(signUpData.getDatePicker());
    }

    private boolean checkUniqueCredentials() {
        if (isUsernameRegistered()) {
            setErrorMessage("Username already registered.");
            setErrorStyle(signUpData.getUsernameTextField());
            return false;
        } else if (isEmailRegistered()) {
            setErrorMessage("Email already registered.");
            setErrorStyle(signUpData.getEmailTextField());
            return false;
        } else if (!signUpData.getRepeatPasswordField().getText().equals(signUpData.getPasswordField().getText())) {
            setErrorMessage("The passwords don't match!");
            setErrorStyle(signUpData.getPasswordField());
            setErrorStyle(signUpData.getRepeatPasswordField());
            return false;
        } else if (signUpData.getDatePicker().getValue() == null) {
            setErrorMessage("Please select a date of birth.");
            setErrorStyle(signUpData.getDatePicker());
            return false;
        }
        return true;
    }

    private boolean isUsernameRegistered() {
        return AI42Main.database.getTable("user").select(row ->
                row.getValue("username").equals(signUpData.getUsernameTextField().getText())).size() == 1;
    }

    private boolean isEmailRegistered() {
        return AI42Main.database.getTable("user").select(row ->
                row.getValue("email").equals(signUpData.getEmailTextField().getText())).size() == 1;
    }

    private void registerUser() {
        setSuccessMessage("Registration successful!");
        HashMap<String, String> data = new HashMap<>();
        data.put("username", signUpData.getUsernameTextField().getText());
        data.put("email", signUpData.getEmailTextField().getText());
        data.put("password", signUpData.getPasswordField().getText());
        data.put("date_of_birth", signUpData.getDatePicker().getValue().toString());
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
            signUpData.getUsernameTextField().setText("");
            signUpData.getEmailTextField().setText("");
            signUpData.getPasswordField().setText("");
            signUpData.getRepeatPasswordField().setText("");
            signUpData.getTermsConditionsCheckbox().setSelected(false);
            signUpData.getDatePicker().setValue(null);
        }));

        // Start the timeline
        timeline.play();
    }

    private void resetErrorFields() {
        clearErrorMessage();
        clearErrorStyle(signUpData.getUsernameTextField());
        clearErrorStyle(signUpData.getEmailTextField());
        clearErrorStyle(signUpData.getPasswordField());
        clearErrorStyle(signUpData.getRepeatPasswordField());
        clearErrorStyle(signUpData.getDatePicker());
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
