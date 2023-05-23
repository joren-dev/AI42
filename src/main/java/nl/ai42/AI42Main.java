package nl.ai42;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import java.io.File;

public class AI42Main extends Application {

    private double x_offset = 0;
    private double y_offset = 0;

    @Override
    public void start(final Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login-and-register.fxml"));

        stage.initStyle(StageStyle.UNDECORATED);

        // grabs your root here
        root.setOnMousePressed(event -> {
            x_offset = event.getSceneX();
            y_offset = event.getSceneY();
        });

        // move window around
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x_offset);
            stage.setY(event.getScreenY() - y_offset);
        });

        final Scene scene = new Scene(root);
        String currentDirectory = System.getProperty("user.dir");


        Image icon = new Image(getClass().getResourceAsStream("images/favicon.png"));
        stage.getIcons().add(icon);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
