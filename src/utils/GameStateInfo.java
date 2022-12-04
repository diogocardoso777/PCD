package utils;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStateInfo implements Serializable {
    private CellInfo[][] cells;
    private ArrayList<Integer> winners;     //id das threads
    private boolean gameFinished;
    //distinguir jogadores humanos dos bots

    public GameStateInfo (CellInfo[][] cells){
        this.cells = cells;
        this.winners = new ArrayList<>();
        this.gameFinished = false;
    }
    public GameStateInfo(ArrayList<Integer> winners, boolean gameFinished){
        this.cells = null;
        this.winners = winners;
        this.gameFinished = gameFinished;

    }
    public CellInfo[][] getCells() {
        return cells;
    }

    public ArrayList<Integer> getWinners() {
        return winners;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }


}
