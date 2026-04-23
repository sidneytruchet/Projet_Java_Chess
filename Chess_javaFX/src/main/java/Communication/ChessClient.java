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
    private ChessController controller;

    public void connecterAuServeur(String adresseIp, int port) {
        try {
            socket = new Socket(adresseIp, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            demarrerEcoute();
        } catch (IOException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }

    public void envoyerCoup(String messageBrut) {
        if (out != null) {
            out.println(messageBrut);


            if (messageBrut.startsWith("MOVE:")) {
                String notation = traduireMoveEnNotation(messageBrut);
                controller.recevoirMessage(notation);
            }

        }
    }

    private void demarrerEcoute() {
        new Thread(() -> {
            try {
                String texteRecu;
                while ((texteRecu = in.readLine()) != null) {
                    final String msg = texteRecu;
                    Platform.runLater(() -> {
                        if (msg.startsWith("SYSTEM:COLOR:")) {
                            String coul = msg.split(":")[2];
                            boolean isWhite = coul.equals("WHITE");
                            controller.setStartingPlayer(isWhite);
                            controller.SetPieces(isWhite);
                            controller.recevoirMessage("[Système] Vous jouez les " + (isWhite ? "BLANCS" : "NOIRS"));
                        }
                        else if (msg.startsWith("MOVE:")) {
                            String[] data = msg.substring(5).split(":");
                            String[] dep = data[0].split(",");
                            String[] arr = data[1].split(",");
                            int r1 = Integer.parseInt(dep[0]), c1 = Integer.parseInt(dep[1]);
                            int r2 = Integer.parseInt(arr[0]), c2 = Integer.parseInt(arr[1]);

                            controller.recevoirCoupAdverse(r1, c1, r2, c2);
                            controller.recevoirMessage(traduireMoveEnNotation(msg));
                        }
                        else if (msg.startsWith("CHAT:")) {

                            controller.recevoirMessage("Adversaire : " + msg.substring(5));
                        }
                        else if (msg.equals("SURRENDER")) {
                            controller.recevoirAbandon();
                        }
                    });
                }
            } catch (IOException e) { System.err.println("Déconnecté."); }
        }).start();
    }

    private String traduireMoveEnNotation(String moveMsg) {

        String[] data = moveMsg.substring(5).split(":");
        String[] arr = data[1].split(",");
        int r = Integer.parseInt(arr[0]);
        int c = Integer.parseInt(arr[1]);

        char col = (char) ('a' + c);
        int lig = 8 - r;
        return "[Coup] " + col + lig;
    }

    public void assignerController(ChessController c) { this.controller = c; }
}