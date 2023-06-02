package nl.ai42;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import nl.ai42.utils.Database;

public class AI42Main extends Application {

    private double x_offset = 0;
    private double y_offset = 0;
    public static Database database = new Database("AI42.db");

    @Override
    public void start(final Stage stage) throws Exception {
        if (AI42Main.database.getTable("user") == null)
            AI42Main.database.createTable("user", new String[]{"username", "email", "password", "date_of_birth", "gender"});

        if (AI42Main.database.getTable("chat") == null)
            AI42Main.database.createTable("chat", new String[]{"username", "chatname"});

        if (AI42Main.database.getTable("chatmsg") == null)
            AI42Main.database.createTable("chatmsg", new String[]{"username", "chatname", "msg_counter", "msg_content", "is_ai", "sent"});

        Parent root = FXMLLoader.load(getClass().getResource("views/login-and-register.fxml"));

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

        Image icon = new Image(getClass().getResourceAsStream("images/favicon.png"));
        stage.getIcons().add(icon);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
