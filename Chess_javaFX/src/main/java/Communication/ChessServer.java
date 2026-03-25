package Communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChessServer {
    public static void main(String[] args) {
        // Lit le port ou utilise 8080 par défaut
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur démarré sur le port " + port + ". En attente de joueurs...");

            // Attente du Joueur 1
            Socket player1Socket = serverSocket.accept();
            System.out.println("Joueur 1 connecté.");

            // Attente du Joueur 2
            Socket player2Socket = serverSocket.accept();
            System.out.println("Joueur 2 connecté.");


            PlayerHandler player1Handler = new PlayerHandler(player1Socket, player2Socket);
            PlayerHandler player2Handler = new PlayerHandler(player2Socket, player1Socket);

            new Thread(player1Handler).start();
            new Thread(player2Handler).start();

            System.out.println("La partie peut commencer.");

        } catch (IOException e) {
            System.err.println("Erreur du serveur : " + e.getMessage());
        }
    }
}