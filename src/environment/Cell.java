package environment;

import com.sun.source.tree.ConditionalExpressionTree;
import game.BotPlayer;
import game.Game;
import game.Player;

import java.io.Serializable;
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

	public void setPlayer(Player player) throws InterruptedException {
		lock.lock();
		while(isOcupied()) {
			System.out.println("Célula de " + position.toString() + " está ocupada pelo jogador " + this.player.toString() + " O Jogador "+ player.toString() + " terá de esperar que a posição fique livre.");
			cellFree.await();
		}
		this.player = player;
		lock.unlock();
		//cellOcupied.signalAll();
	}

	public void movePlayer(Player player, Cell to){
		this.lock.lock();
		to.lock.lock();

		if(!to.isOcupied()) {
			try {
				to.setPlayer(player);
				this.player = null;

				cellFree.signalAll();				//notificar as threads à espera que a CELL fique livre
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}else{
			Player p = to.getPlayer();
			if(p.getCurrentStrength() != 0)
				fight(this.player, p);
				//cellOcupied.signalAll();
		}

		to.lock.unlock();
		this.lock.unlock();

		game.notifyChange();

	}
	public void fight(Player player1, Player player2){
		int comparison = Byte.compare(player1.getCurrentStrength(), player2.getCurrentStrength());
		if(comparison > 0)
			fightStrengthChanger(player1, player2);
		else if(comparison < 0)
			fightStrengthChanger(player2, player1);
		else{
			if(Math.random() > 0.5) fightStrengthChanger(player1, player2);
			else fightStrengthChanger(player2, player1);
		}
	}

	public void fightStrengthChanger(Player winner, Player loser){
		winner.addStrength(loser.getCurrentStrength());

		System.out.println("Killed player : " + loser.getIdentification());
		loser.killPlayer();
	}
	

}
