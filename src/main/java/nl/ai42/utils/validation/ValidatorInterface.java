package nl.ai42.utils.validation;

import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public interface ValidatorInterface {
    default void setErrorMessage(Label label, String message) {
        label.setText(message);
        label.setStyle("-fx-text-fill: red;");
    }

    default void setSuccessMessage(Label label, String message) {
        label.setText(message);
        label.setStyle("-fx-text-fill: green;");
    }

    default void clearErrorMessage(Label label) {
        label.setText("");
    }

    default void setErrorStyle(Control control) {
        control.setStyle("-fx-border-color: red;");
    }

    default void clearErrorStyle(Control control) {
        control.setStyle(null);
    }

    default boolean isEmptyTextField(TextField textField) {
        return textField.getText().isBlank();
    }

    default boolean isEmptyDatePickerField(DatePicker datePicker) {
        return datePicker.getValue() == null;
    }
}
