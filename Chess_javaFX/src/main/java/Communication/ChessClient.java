package Communication;

import javafx.application.Platform;
import org.devops.chess_javafx.ChessController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class ChessClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String maCouleur = "";


    private ChessController controller;

    public void connecterAuServeur(String adresseIp, int port) {
        try {
            socket = new Socket(adresseIp, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connecte au serveur.");
            demarrerEcoute();
        } catch (IOException e) {
            System.err.println("Impossible de se connecter au serveur : " + e.getMessage());
        }
    }


    public void envoyerCoup(String coup) {
        if (out != null) {
            out.println(coup);
        }
    }


    private void demarrerEcoute() {
        new Thread(() -> {
            try {
                String texteRecu;
                while ((texteRecu = in.readLine()) != null) {

                    final String messageFinal = texteRecu;

                    Platform.runLater(() -> {

                        if (messageFinal.startsWith("SYSTEM:COLOR:")) {


                            maCouleur = messageFinal.split(":")[2];

                            if (maCouleur.equals("WHITE")) {
                                System.out.println("[Systeme] Vous jouez les BLANCS ! C'est a vous de commencer.");
                                controller.SetPieces(true);
                            } else {
                                System.out.println("[Systeme] Vous jouez les NOIRS ! Attendez le coup de l'adversaire.");
                                controller.SetPieces(false);
                            }

                        } else {

                            System.out.println("[Adversaire] " + messageFinal);
                        }
                    });
                }
            } catch (IOException e) {
                System.err.println("Deconnexion du serveur.");
            }
        }).start();
    }

    public void assignerController(ChessController Controller) {
        controller = Controller;
    }
}