package game;

import connection.DealWithClient;
import environment.Cell;
import environment.Direction;

import static java.lang.Thread.sleep;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */
public class PhoneyHumanPlayer extends Player{
	//private String moveDir=null;

	public PhoneyHumanPlayer(int id, Game game) {
		super(id, game);
		this.originalStrength = 5;
		this.currentStrength = originalStrength;
	}

	public boolean isHumanPlayer() {
		return true;
	}



}
