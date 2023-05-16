module nl.ai42.ai42 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens nl.ai42.ai42 to javafx.fxml;
    exports nl.ai42.ai42;
}