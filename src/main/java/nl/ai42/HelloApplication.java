package nl.ai42;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloApplication extends Application {

    private double x_offset = 0;
    private double y_offset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/hello-view.fxml"));

        stage.initStyle(StageStyle.UNDECORATED);

        // grabs your root here
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x_offset = event.getSceneX();
                y_offset = event.getSceneY();
            }
        });

        // move window around
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - x_offset);
                stage.setY(event.getScreenY() - y_offset);
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
