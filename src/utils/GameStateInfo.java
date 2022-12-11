package utils;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStateInfo implements Serializable {
    private CellInfo[][] cells;
    private boolean gameFinished;
    private boolean isWinner;

    public GameStateInfo (CellInfo[][] cells){
        this.cells = cells;
        this.gameFinished = false;
    }
    public GameStateInfo (CellInfo[][] cells, boolean isWinner){
        this.gameFinished = true;
        this.cells = cells;
        this.isWinner = isWinner;
    }

    public CellInfo[][] getCells() {
        return cells;
    }

    public boolean gameIsFinished() {
        return gameFinished;
    }

}
