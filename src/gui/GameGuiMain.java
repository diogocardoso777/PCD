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

	public static GameGuiMain GAMEGUI_INSTANCE;



	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);

		buildGui();


	}


	public static GameGuiMain getGameGuiInstance(){
		if(GAMEGUI_INSTANCE == null){
			GAMEGUI_INSTANCE = new GameGuiMain();
			GAMEGUI_INSTANCE.init();
			//GAMEGUI_INSTANCE.init();
		}
		return GAMEGUI_INSTANCE;
	}
	public Game getGame(){
		return this.game;
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

	}

	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}

	public static void main(String[] args) {
		GameGuiMain game = getGameGuiInstance();

	}

}
