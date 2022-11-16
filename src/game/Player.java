package game;



import environment.Cell;
import environment.Coordinate;
import environment.Direction;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player {

	protected  Game game;

	private int id;

	private byte currentStrength;
	protected byte originalStrength;

	// TODO: get player position from data in game
	public Cell getCurrentCell() {
		for (int x = 0; x < Game.DIMX; x++)
			for (int y = 0; y < Game.DIMY; y++) {
				Player p = game.board[x][y].getPlayer();
				if (p != null && p.equals(this))
					return game.getCell(new Coordinate(x,y));
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


	//direction random
	public void move(Direction dir){
		Cell from = getCurrentCell();
		Coordinate coordTo = new Coordinate(from.getPosition().x + dir.getVector().x, from.getPosition().y + dir.getVector().y);
		if(!coordTo.isValidPosition()) return;
		Cell to = game.getCell(coordTo);
		from.movePlayer(this, to);
	/*	from.takePlayer(p);
		try {
			to.setPlayer(p);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	
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

	public void addStrength(byte sumStrength){
		byte resultStrength = (byte) (this.currentStrength + sumStrength);
		if (resultStrength > 10)
			this.currentStrength = (byte) 10;
		else
			this.currentStrength = resultStrength;
	}

	public void killPlayer(){
		this.currentStrength = 0;
	}

}
