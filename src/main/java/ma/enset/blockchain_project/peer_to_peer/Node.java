package ma.enset.blockchain_project.peer_to_peer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Node {
    private ArrayList<Peer> peers;
    private ServerSocket serverSocket;

    public Node(int port) throws IOException {
        this.peers = new ArrayList<>();
        this.serverSocket = new ServerSocket(port);
        new Thread(this::listenForConnections).start();
    }

    private void listenForConnections() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Peer peer = new Peer(socket);
                peers.add(peer);
                new Thread(peer::listen).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcast(String message) {
        for (Peer peer : peers) {
            peer.sendMessage(message);
        }
    }

    public void addPeer(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        Peer peer = new Peer(socket);
        peers.add(peer);
        new Thread(peer::listen).start();
    }
}
