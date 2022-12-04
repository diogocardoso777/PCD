package connection;

import environment.Cell;
import game.Game;
import game.Player;
import gui.GameGuiMain;
import utils.CellInfo;
import utils.GameStateInfo;

import java.io.*;
import java.net.Socket;

public class DealWithClient extends Thread{
    private Player player;
    private Socket socket;
    private BufferedReader in;
    private ObjectOutputStream out;
    private GameStateInfo gameState;
    private int playerId;
    private Game game;

    public DealWithClient(Socket socket, Game game, int id){
        this.socket = socket;
        this.game = game;
        this.playerId = id;
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

    private GameStateInfo sendBoardState(){
        Cell cells [][] = game.getBoard();

        CellInfo cellInfos [][] = new CellInfo[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length ; j++) {
                if(cells[i][j].getPlayer()!=null){
                    if(cells[i][j].getPlayer().getIdentification() == playerId)
                        cellInfos[i][j] = new CellInfo(cells[i][j].getPlayer().getCurrentStrength(), true);
                    else cellInfos[i][j] = new CellInfo(cells[i][j].getPlayer().getCurrentStrength(), false);
                }

            }
        }

        gameState = new GameStateInfo(cellInfos);
        return gameState;
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
