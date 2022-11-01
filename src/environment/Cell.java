package environment;

import com.sun.source.tree.ConditionalExpressionTree;
import game.Game;
import game.Player;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
	private Coordinate position;
	private Game game;
	private Player player=null;

	//private Lock lock = new ReentrantLock();
	
	public Cell(Coordinate position,Game g) {
		super();
		this.position = position;
		this.game=g;
	}

	public Coordinate getPosition() {
		return position;
	}

	public boolean isOcupied() {
		return player!=null;
	}


	public Player getPlayer() {
		return player;
	}

	// Should not be used like this in the initial state: cell might be occupied, must coordinate this operation
	public void setPlayer(Player player) throws InterruptedException {
		while(isOcupied()) {
			//System.out.println("Célula de " + position.toString() + " está ocupada pelo jogador " + this.player.toString() + "\n" +
			//		"Jogador "+ player.toString() + " terá de esperar que a posição fique livre.");
			wait();
		}
		this.player = player;
		notifyAll();
	}
	
	

}
