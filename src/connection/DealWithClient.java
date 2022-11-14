package connection;

import game.Player;
import gui.GameGuiMain;

import java.io.*;
import java.net.Socket;

public class DealWithClient extends Thread{
    private Player player;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private GameGuiMain gameGuiMain;

    public DealWithClient(Socket socket, GameGuiMain gameGuiMain){
        this.socket = socket;
        this.gameGuiMain = gameGuiMain;
    }
    private void doConnections(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())),
                true);
    }
    public void serve(){
        out.println(gameGuiMain);
    }

    @Override
    public void run(){
        try {
            doConnections(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
