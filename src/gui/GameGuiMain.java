package gui;

import java.util.Observable;
import java.util.Observer;
import game.Game;
import game.PhoneyHumanPlayer;

import javax.swing.JFrame;

public class GameGuiMain implements Observer {
	private JFrame frame = new JFrame("pcd.io");
	private BoardJComponent boardGui;
	private Game game;

	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);

		buildGui();

	}

	private void buildGui() {
		boardGui = new BoardJComponent(game);
		frame.add(boardGui);


		frame.setSize(800,800);
		frame.setLocation(0, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init()  {
		frame.setVisible(true);

		// Demo players, should be deleted
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.addPlayerToGame(new PhoneyHumanPlayer(1, game));
		game.addPlayerToGame(new PhoneyHumanPlayer(2, game));
		game.addPlayerToGame(new PhoneyHumanPlayer(3, game));
		game.addPlayerToGame(new PhoneyHumanPlayer(4, game));
		game.addPlayerToGame(new PhoneyHumanPlayer(5, game));
		game.addPlayerToGame(new PhoneyHumanPlayer(6, game));
		game.addPlayerToGame(new PhoneyHumanPlayer(7, game));
		game.addPlayerToGame(new PhoneyHumanPlayer(8, game));
		game.addPlayerToGame(new PhoneyHumanPlayer(9, game));
	}

	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}

	public static void main(String[] args) {
		GameGuiMain game = new GameGuiMain();
		game.init();

	}

}
