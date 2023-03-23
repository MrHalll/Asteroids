package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Label label = new Label("Press space to play");
        label.setTextFill(Color.rgb(255, 255, 255, 1));

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");
        root.getChildren().add(label);
        Scene scene = new Scene(root, 800, 700);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}