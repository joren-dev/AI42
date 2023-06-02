package nl.ai42;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import nl.ai42.managers.SceneManager;
import nl.ai42.utils.Database;

public class AI42Main extends Application {

    private double x_offset = 0;
    private double y_offset = 0;
    public static Database database = new Database("AI42.db");
    private SceneManager scene_manager;

    @Override
    public void start(final Stage stage) throws Exception {
        initialize_database();

        stage.initStyle(StageStyle.UNDECORATED);

        scene_manager = SceneManager.getInstance(stage);
        scene_manager.loadScene("login-and-register.fxml");

        // grabs your root here
        scene_manager.getCurrent_scene().getRoot().setOnMousePressed(event -> {
            x_offset = event.getSceneX();
            y_offset = event.getSceneY();
        });

        // move window around
        scene_manager.getCurrent_scene().getRoot().setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x_offset);
            stage.setY(event.getScreenY() - y_offset);
        });

        Image icon = new Image(getClass().getResourceAsStream("images/favicon.png"));
        stage.getIcons().add(icon);

        stage.setScene(scene_manager.getCurrent_scene());

        stage.show();
    }

    private void initialize_database() throws Exception {
        database = new Database("AI42.db");

        if (database.getTable("user") == null)
            database.createTable("user", new String[]{"username", "email", "password", "date_of_birth", "gender"});

        if (database.getTable("chat") == null)
            database.createTable("chat", new String[]{"username", "chatname"});

        if (database.getTable("chatmsg") == null)
            database.createTable("chatmsg", new String[]{"username", "chatname", "msg_counter", "msg_content", "is_ai", "sent"});
    }

    public static void main(String[] args) {
        launch(args);
    }
}
