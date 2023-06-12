package nl.ai42.utils.validation.utility;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StatusUtility {
    public static void setErrorMessage(Label label, String message) {
        label.setText(message);
        label.setStyle("-fx-text-fill: red;");
    }

    public static void setSuccessMessage(Label label, String message) {
        label.setText(message);
        label.setStyle("-fx-text-fill: green;");
    }

    public static void clearErrorMessage(Label label) {
        label.setText("");
    }

    public static void setErrorStyle(Control control) {
        control.setStyle("-fx-border-color: red;");
    }

    public static void clearErrorStyle(Control control) {
        control.setStyle(null);
    }

    public static boolean isEmptyTextField(TextField textField) {
        return textField.getText().isBlank();
    }
}
