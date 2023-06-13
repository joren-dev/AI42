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
        if (isEmptyField(usernameTextField) ||
                isEmptyField(emailTextField))
            return false;

        if (isEmptyField(repeatPasswordField) || isEmptyField(passwordField))
            return false;

        if (datePicker.getValue() == null)
            return false;

        return true;
    }

    public static boolean validateUsername(TextField usernameTextField) {
        return PatternMatchUtility.isValidName(usernameTextField.getText());
    }

    public static boolean validateEmail(TextField emailTextField) {
        return PatternMatchUtility.isValidEmail(emailTextField.getText());
    }

    public static boolean validatePassword(TextField passwordField) {
        return PatternMatchUtility.isValidPassword(passwordField.getText());
    }

    public static boolean validateDateOfBirth(DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            if (!PatternMatchUtility.isValidDate(datePicker.getValue().toString())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isEmptyField(TextField textField) {
        return textField.getText().isBlank();
    }
}
