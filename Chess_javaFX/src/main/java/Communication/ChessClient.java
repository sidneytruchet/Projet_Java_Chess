package Communication;

import javafx.application.Platform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChessClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;


    public void connecterAuServeur(String adresseIp, int port) {
        try {
            socket = new Socket(adresseIp, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connecté au serveur.");
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
                String coupAdverse;
                while ((coupAdverse = in.readLine()) != null) {
                    final String coupRecu = coupAdverse;


                    Platform.runLater(() -> {
                        System.out.println("Application du coup adverse sur le plateau : " + coupRecu);

                    });
                }
            } catch (IOException e) {
                System.err.println("Déconnexion du serveur.");
            }
        }).start();
    }
}