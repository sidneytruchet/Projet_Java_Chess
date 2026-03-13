package org.devops.chess_javafx;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Interface.Chessboard;

public class HelloController {

    // On cible la couche du fond pour mettre l'image
    @FXML
    private Pane backgroundPane;

    @FXML
    public void initialize() {
        try {
            URL imageUrl = getClass().getResource("magnus.jpg");

            if (imageUrl != null) {
                String css = "-fx-background-image: url('" + imageUrl.toExternalForm() + "'); " +
                        "-fx-background-size: cover; " +
                        "-fx-background-position: center center;";
                backgroundPane.setStyle(css);
            } else {
                System.out.println("ERREUR : L'image magnus.jpg n'a pas été trouvée.");
            }
        } catch (Exception e) {
            System.out.println("Erreur critique lors du chargement : " + e.getMessage());
        }
    }

    @FXML
    protected void onOnlineGameClick() {
        System.out.println("Transition vers l'échiquier en cours...");
        try {
            // 1. On va chercher le fichier FXML exactement là où la classe Chessboard l'attend
            FXMLLoader fxmlLoader = new FXMLLoader(Chessboard.class.getResource("chessboard-view.fxml"));

            // 2. On génère la nouvelle scène (1280 x 720)
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

            // 3. On récupère la fenêtre principale grâce à notre fond "Minecraft"
            Stage stage = (Stage) backgroundPane.getScene().getWindow();

            // 4. On remplace la scène et on met à jour le titre
            stage.setTitle("Chess JavaFX - Partie en cours");
            stage.setScene(scene);
            stage.setMaximized(true); // On maintient le plein écran

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du plateau : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void onQuitClick() {
        Platform.exit();
    }
}