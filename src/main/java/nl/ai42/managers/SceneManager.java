package nl.ai42.managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;
    private Scene currentScene;

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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadScene(String fxmlFile) {
        try {
            String resourcePath = "/nl/ai42/views/" + fxmlFile;
            Parent root = FXMLLoader.load(getClass().getResource(resourcePath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            currentScene = scene;
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // grabs your root here
        this.getCurrentScene().getRoot().setOnMousePressed(event -> {
            x_offset = event.getSceneX();
            y_offset = event.getSceneY();
        });

        // move window around
        this.getCurrentScene().getRoot().setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x_offset);
            stage.setY(event.getScreenY() - y_offset);
        });
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
