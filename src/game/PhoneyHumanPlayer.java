package game;

import environment.Cell;
import environment.Direction;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */
public class PhoneyHumanPlayer extends Player{
	public PhoneyHumanPlayer(int id, Game game) {
		super(id, game);
	}

	public boolean isHumanPlayer() {
		return true;
	}

	//TODO thread method  implementation

	/*
	@Override
	public void run() {
		game.addPlayerToGame(this);
		
		//System.out.println("id : " + this.getId() + "strength : " + this.getCurrentStrength());
	}*/
}
