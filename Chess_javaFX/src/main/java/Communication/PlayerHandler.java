package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {
    private final Socket currentSocket;
    private final Socket opponentSocket;

    public PlayerHandler(Socket currentSocket, Socket opponentSocket) {
        this.currentSocket = currentSocket;
        this.opponentSocket = opponentSocket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
                PrintWriter out = new PrintWriter(opponentSocket.getOutputStream(), true)
        ) {
            String message;

            while ((message = in.readLine()) != null) {
                System.out.println("Relais du coup : " + message);

                out.println(message);
            }
        } catch (IOException e) {
            System.err.println("Connexion interrompue avec un joueur.");
        }
    }
}