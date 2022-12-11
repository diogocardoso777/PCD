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
    private Socket socket;
    private ObjectOutputStream out;
    private int playerId;
    private Game game;

    public DealWithClient(Socket socket, Game game, int id){
        this.socket = socket;
        this.game = game;
        this.playerId = id;
    }
    private void doConnections(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
    }
    private CellInfo[][] generateCells(){
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
        return cellInfos;
    }
    private GameStateInfo clientBoardState(){
        CellInfo[][] cells = generateCells();
        /*
        if(game.isGameOver() || game.isWinner(playerId))
            return new GameStateInfo(cells, false);
        else if(game.isAlive(playerId))
            return new GameStateInfo(cells);
        else
            return new GameStateInfo(cells, true);*/
        if(game.isWinner(playerId))
            return new GameStateInfo(cells, true);
        if(game.isGameOver() || !game.isAlive(playerId))
            return new GameStateInfo(cells, false);

        return new GameStateInfo(cells);

    }

    @Override
    public void run() {
        try {
            doConnections(socket);
            while (!isInterrupted()) {
                sleep(Game.REFRESH_INTERVAL);
                out.writeObject(clientBoardState());
            }

        } catch (IOException | InterruptedException e) {
            try {
                out.writeObject(clientBoardState());
                System.out.println("socket closing by DealWithClient");
                socket.close();
            } catch (IOException ex) {
            }
        }
    }
}
