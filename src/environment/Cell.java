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

	private Lock lock = new ReentrantLock();
	private Condition cellOcupied = lock.newCondition();
	private Condition cellFree = lock.newCondition();
	
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

	public synchronized void setPlayer(Player player) throws InterruptedException {
		while(isOcupied()) {
			//System.out.println("Célula de " + position.toString() + " está ocupada pelo jogador " + this.player.toString());
			//		"Jogador "+ player.toString() + " terá de esperar que a posição fique livre.");
			wait();
		}
		this.player = player;
		notifyAll();
	}

	public synchronized void movePlayer(Player player, Cell to){
		this.lock.lock();
		to.lock.lock();

		if(!to.isOcupied()) {
			try {
				to.setPlayer(player);
				this.setPlayer(null);

				cellFree.signalAll();				//notificar as threads à espera que a CELL fique livre
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}else{
			if(to.getPlayer().getCurrentStrength() != -1){
				//lutar
			}
			else{
				return;			//caso em que o jogador dentro da CELL está morto
			}

			//falta ver se esta um player morto na cell
			//fazer a interaçao entre players
		}

		to.lock.unlock();
		this.lock.unlock();

		game.notifyChange();

	}
	
	

}
