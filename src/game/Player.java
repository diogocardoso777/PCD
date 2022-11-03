package game;



import environment.Cell;
import environment.Coordinate;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player extends Thread{


	protected  Game game;

	private int id;

	private byte currentStrength;
	protected byte originalStrength;

	// TODO: get player position from data in game
	public Cell getCurrentCell() {
		for (int x = 0; x < Game.DIMX; x++)
			for (int y = 0; y < Game.DIMY; y++) {
				if (game.board[x][y].getPlayer().equals(this))
					return game.board[x][y].getPlayer().getCurrentCell();
			}
		return null;
	}

	public Player(int id, Game game) {
		super();
		this.id = id;
		this.game=game;
		originalStrength= (byte) (Math.floor(Math.random() * 3) + 1);
		currentStrength=originalStrength;
	}

	public abstract boolean isHumanPlayer();
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", currentStrength=" + currentStrength + ", getCurrentCell()=" + getCurrentCell()
		+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public byte getCurrentStrength() {
		return currentStrength;
	}


	public int getIdentification() {
		return id;
	}


}
