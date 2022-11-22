package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WinDialog {
    private JFrame frame;
    public WinDialog(ArrayList<Integer> winners){
        frame = new JFrame();
        frame.setLayout(new GridLayout(4,1));
        frame.add(new JLabel("Game Finish"));
        addWinners(winners);
        frame.pack();
        frame.setVisible(true);
    }
    public void addWinners(ArrayList<Integer> winners){
        for(Integer winner : winners){
            JLabel label = new JLabel(winners.indexOf(winner)+1 + "º: " + winner);
            frame.add(label);
        }
    }
}
