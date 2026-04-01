package org.devops.chess_javafx;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

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

        // "127.0.0.1" par défaut (l'adresse locale machine)
        TextInputDialog dialog = new TextInputDialog("127.0.0.1");
        dialog.setTitle("Connexion au serveur");
        dialog.setHeaderText("Rejoindre une partie en 1v1");
        dialog.setContentText("Entrez l'adresse IP du serveur :");


        Optional<String> result = dialog.showAndWait();


        if (result.isPresent()) {
            String ipServeur = result.get();
            System.out.println("Tentative de connexion à l'IP : " + ipServeur);

            try {

                Communication.ChessClient client = new Communication.ChessClient();
                client.connecterAuServeur(ipServeur, 8080);

                FXMLLoader fxmlLoader = new FXMLLoader(ChessController.class.getResource("chess-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

                ChessController controller = fxmlLoader.getController();
                controller.assignerClient(client);
                client.assignerController(controller);

                Stage stage = (Stage) backgroundPane.getScene().getWindow();
                stage.setTitle("Chess JavaFX - Partie réseau (Connecté à " + ipServeur + ")");
                stage.setScene(scene);
                stage.setMaximized(true);

            } catch (Exception e) {
                System.out.println("Erreur réseau ou chargement du plateau : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onQuitClick() {
        Platform.exit();
    }
}