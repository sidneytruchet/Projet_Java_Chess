package org.devops.chess_javafx;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import java.net.URL;

public class HelloController {


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
        System.out.println("Bouton Jouer en Ligne cliqué");
    }

    @FXML
    protected void onQuitClick() {
        Platform.exit();
    }
}