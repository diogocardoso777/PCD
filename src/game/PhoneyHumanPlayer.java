package game;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */
public class PhoneyHumanPlayer extends Player {
	public PhoneyHumanPlayer(int id, Game game) {
		super(id, game);
	}

	public boolean isHumanPlayer() {
		return true;
	}

	//TODO thread method  implementation

	@Override
	public void run() {
		System.out.println("id : " + this.getId() + "strength : " + this.getCurrentStrength());
	}
}
