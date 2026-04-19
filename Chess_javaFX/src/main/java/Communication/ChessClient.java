package Communication;

import javafx.application.Platform;
import org.devops.chess_javafx.ChessController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

                        System.out.println(messageFinal);

                        if (messageFinal.startsWith("SYSTEM:COLOR:")) {

                            maCouleur = messageFinal.split(":")[2];

                            if (maCouleur.equals("WHITE")) {
                                controller.setStartingPlayer(true);
                                controller.SetPieces(true);
                                controller.recevoirMessage("[Systeme] Vous jouez les BLANCS ! C'est a vous de commencer.");
                            } else {
                                controller.setStartingPlayer(false);
                                controller.SetPieces(false);
                                controller.recevoirMessage("[Systeme] Vous jouez les NOIRS ! Attendez le coup de l'adversaire.");
                            }

                        } else if (messageFinal.startsWith("MOVE:")) {
                            String[] data = messageFinal.substring(5).split(":");
                            String[] depart = data[0].split(",");
                            String[] arrivee = data[1].split(",");

                            int r1 = Integer.parseInt(depart[0]);
                            int c1 = Integer.parseInt(depart[1]);
                            int r2 = Integer.parseInt(arrivee[0]);
                            int c2 = Integer.parseInt(arrivee[1]);

                            if (controller != null) {
                                controller.recevoirCoupAdverse(r1, c1, r2, c2);
                                controller.recevoirMessage(messageFinal);
                            }

                        } else if (messageFinal.equals("SURRENDER")) {
                            if (controller != null) {
                                controller.recevoirAbandon();
                            }
                        } else {
                            if (controller != null) {
                                controller.recevoirMessage(messageFinal);
                            }
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