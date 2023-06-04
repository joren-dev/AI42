package nl.ai42.managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;
    private Scene current_scene;

    private double x_offset = 0;
    private double y_offset = 0;

    private SceneManager() {
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(final Stage stage)
    {
        this.stage = stage;
    }

    public void loadScene(final String fxmlFile) {
        try {
            final String resource_path = "/nl/ai42/views/" + fxmlFile;
            final Parent root = FXMLLoader.load(getClass().getResource(resource_path));

            final Scene scene = new Scene(root);
            stage.setScene(scene);

            // set current scene
            current_scene = scene;

            // Display scene
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // grabs your root here
        this.getCurrent_scene().getRoot().setOnMousePressed(event -> {
            x_offset = event.getSceneX();
            y_offset = event.getSceneY();
        });

        // move window around
        this.getCurrent_scene().getRoot().setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x_offset);
            stage.setY(event.getScreenY() - y_offset);
        });
    }

    public Scene getCurrent_scene()
    {
        return current_scene;
    }
}
