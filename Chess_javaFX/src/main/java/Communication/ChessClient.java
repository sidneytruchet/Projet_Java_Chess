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

            System.out.println("Connecté au serveur de jeu !");
            demarrerEcoute();


            envoyerMessage("Bonjour ! La connexion est établie.");

        } catch (IOException e) {
            System.err.println("Impossible de se connecter : " + e.getMessage());
        }
    }


    public void envoyerMessage(String texte) {
        if (out != null) {
            out.println(texte);

            System.out.println("[Moi] " + texte);
        }
    }


    private void demarrerEcoute() {
        new Thread(() -> {
            try {
                String texteRecu;

                while ((texteRecu = in.readLine()) != null) {

                    final String messageFinal = texteRecu;


                    Platform.runLater(() -> {
                        System.out.println("[Adversaire] " + messageFinal);



                    });
                }
            } catch (IOException e) {
                System.err.println("Déconnexion du serveur.");
            }
        }).start();
    }
}