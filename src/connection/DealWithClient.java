package connection;

import environment.Cell;
import game.Game;
import game.Player;
import gui.GameGuiMain;

import java.io.*;
import java.net.Socket;

public class DealWithClient extends Thread{
    private Player player;
    private Socket socket;
    private BufferedReader in;
    private ObjectOutputStream out;

    private Game game;

    public DealWithClient(Socket socket, Game game){
        this.socket = socket;
        this.game = game;
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

    private byte [][] sendBoardState(){
        Cell cells [][] = game.getBoard();

        byte coords [][] = new byte[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length ; j++) {
                if(cells[i][j].getPlayer() != null) coords[i][j] = cells[i][j].getPlayer().getCurrentStrength();
                else coords[i][j] = -1;
            }
        }

        return coords;
    }

    @Override
    public void run(){
        try {
            doConnections(socket);
            while(!isInterrupted()) {
                sleep(Game.REFRESH_INTERVAL);
                out.writeObject(sendBoardState());
            }
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
