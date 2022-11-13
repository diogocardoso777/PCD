package gui;

import java.util.Observable;
import java.util.Observer;

import game.BotPlayer;
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
		BotPlayer p1 = new BotPlayer(1, game);
		BotPlayer p2 = new BotPlayer(2, game);
		BotPlayer p3 = new BotPlayer(3, game);
		BotPlayer p4 = new BotPlayer(4, game);
		BotPlayer p5 = new BotPlayer(5, game);
		BotPlayer p6 = new BotPlayer(6, game);
		BotPlayer p7 = new BotPlayer(7, game);
		BotPlayer p8 = new BotPlayer(8, game);
		BotPlayer p9 = new BotPlayer(9, game);
		BotPlayer p10 = new BotPlayer(10, game);

		p2.start();
		p1.start();
		p3.start();
		p4.start();
		p5.start();
		p6.start();
		p7.start();
		p8.start();
		p9.start();
		p10.start();


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
