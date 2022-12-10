package Client;

import game.Game;
import utils.GameStateInfo;

import javax.swing.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ClientGui implements Observer {
    private BoardJComponentPlayer board;
    private int DIMX;
    private int DIMY;
    private Client client;
    private JFrame frame = new JFrame("pcd.io client");
    public ClientGui(int DIMX, int DIMY, Client client){
        this.DIMX = DIMX;
        this.DIMY = DIMY;
        this.client = client;
        client.addObserver(this);
        buildGui();

    }

    public void buildGui(){
        board = new BoardJComponentPlayer(DIMX, DIMY, client.getStateInfo());
        frame.add(board);

        frame.setSize(800,800);
        frame.setLocation(0, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init()  {
        frame.setVisible(true);
    }

    public BoardJComponentPlayer getBoard(){
        return board;
    }

    @Override
    public void update(Observable o, Object arg) {
        board.setGameState(client.getStateInfo());
        board.repaint();
    }


}
