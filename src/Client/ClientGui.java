package Client;

import game.Game;
import utils.GameStateInfo;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ClientGui implements Observer {
    private BoardJComponentPlayer board;
    private int DIMX;
    private int DIMY;
    private Client client;
    private GameStateInfo stateInfo;
    private JFrame frame = new JFrame("pcd.io client");
    public ClientGui(int DIMX, int DIMY, GameStateInfo info, Client client){
        this.DIMX = DIMX;
        this.DIMY = DIMY;
        stateInfo = info;
        buildGui();
        this.client = client;
        client.addObserver(this);

    }
    public void buildGui(){
        board = new BoardJComponentPlayer(DIMX, DIMY, stateInfo);
        frame.add(board);


        frame.setSize(800,800);
        frame.setLocation(0, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void updateGui(){
        board = new BoardJComponentPlayer(DIMX, DIMY, getStateInfo());
        board.set
        this.stateInfo = getStateInfo();

    }

    public void init()  {
        frame.setVisible(true);


    }
    public GameStateInfo getStateInfo(){
        return client.getStateInfo();
    }
    @Override
    public void update(Observable o, Object arg) {
        updateGui();
        System.out.println(stateInfo);
        board.repaint();
    }
}
