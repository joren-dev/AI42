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

    private SceneManager(Stage stage) {
        this.stage = stage;
    }

    public static SceneManager getInstance(Stage stage) {
        if (instance == null) {
            instance = new SceneManager(stage);
        }
        return instance;
    }

    public void loadScene(String fxmlFile) {
        try {
            String resourcePath = "/nl/ai42/views/" + fxmlFile;
            Parent root = FXMLLoader.load(getClass().getResource(resourcePath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            current_scene = scene;
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchScene(String fxmlFile) {
        loadScene(fxmlFile);
    }

    public Scene getCurrent_scene() {
        return current_scene;
    }
}
