package nl.ai42.utils.validation.utility;

import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ValidationUtility {
    public static boolean validateTermsConditions(CheckBox termsConditionsCheckbox) {
        if (!termsConditionsCheckbox.isSelected()) {
            return false;
        }
        return true;
    }

    public static boolean validateRequiredFields(TextField usernameTextField, TextField emailTextField,
                                                 TextField passwordField, TextField repeatPasswordField,
                                                 DatePicker datePicker) {
        if (isEmptyField(usernameTextField) || isEmptyField(emailTextField) ||
                isEmptyField(passwordField) || isEmptyField(repeatPasswordField) ||
                datePicker.getValue() == null) {
            return false;
        }
        return true;
    }

    public static boolean validateUsername(TextField usernameTextField) {
        if (!PatternMatchUtility.is_valid_name(usernameTextField.getText())) {
            return false;
        }
        return true;
    }

    public static boolean validateEmail(TextField emailTextField) {
        if (!PatternMatchUtility.is_valid_email(emailTextField.getText())) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(TextField passwordField) {
        if (!PatternMatchUtility.is_valid_password(passwordField.getText())) {
            return false;
        }
        return true;
    }

    public static boolean validateDateOfBirth(DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            if (!PatternMatchUtility.is_valid_date(datePicker.getValue().toString())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isEmptyField(TextField textField) {
        return textField.getText().isBlank();
    }
}
