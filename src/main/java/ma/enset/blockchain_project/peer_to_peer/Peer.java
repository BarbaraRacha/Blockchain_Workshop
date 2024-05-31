package ma.enset.blockchain_project.peer_to_peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Peer {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Peer(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void listen() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                // Process incoming message
                System.out.println("Received: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}

