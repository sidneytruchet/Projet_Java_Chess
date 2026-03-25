package org.devops.chess_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    static Stage currentStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Taille de base HD au lieu de 320x240
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setTitle("Chess JavaFX - LP DevOps");
        stage.setScene(scene);

        currentStage = stage;

        // Lance la fenêtre maximisée (prend tout l'écran)
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void ChangeScene(FXMLLoader fxmlLoader) throws IOException {
        // Taille de base HD au lieu de 320x240
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        currentStage.setScene(scene);
        currentStage.setMaximized(true);

    }
}