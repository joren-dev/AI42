module nl.ai42 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens nl.ai42 to javafx.fxml;
    exports nl.ai42;
    exports nl.ai42.controllers;
    exports nl.ai42.utils;
    exports nl.ai42.managers;
    opens nl.ai42.controllers to javafx.fxml;

}