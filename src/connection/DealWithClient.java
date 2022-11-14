package connection;

import game.Player;
import gui.GameGuiMain;

import java.io.*;
import java.net.Socket;

public class DealWithClient extends Thread{
    private Player player;
    private Socket socket;
    private BufferedReader in;
    private ObjectOutputStream out;

    private GameGuiMain gameGuiMain;

    public DealWithClient(Socket socket, GameGuiMain gameGuiMain){
        this.socket = socket;
        this.gameGuiMain = gameGuiMain;
    }
    private void doConnections(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new ObjectOutputStream(socket.getOutputStream());
    }
    public void serve(){
       // out.println(gameGuiMain);
        //devemos mandar o board ou o game
        //sendo assim n sei se a cell tem de ser Serializable ou o game
        //Serializable é necessário para fazer o canal de objetos
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
