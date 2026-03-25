package Communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChessServer {
    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur démarre sur le port " + port + ". En attente de joueurs...");

            Socket player1Socket = serverSocket.accept();
            System.out.println("Joueur 1 connecte.");

            Socket player2Socket = serverSocket.accept();
            System.out.println("Joueur 2 connecte. Attribution des couleurs...");


            PrintWriter out1 = new PrintWriter(player1Socket.getOutputStream(), true);
            PrintWriter out2 = new PrintWriter(player2Socket.getOutputStream(), true);


            if (Math.random() < 0.5) {
                out1.println("SYSTEM:COLOR:WHITE");
                out2.println("SYSTEM:COLOR:BLACK");
            } else {
                out1.println("SYSTEM:COLOR:BLACK");
                out2.println("SYSTEM:COLOR:WHITE");
            }


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