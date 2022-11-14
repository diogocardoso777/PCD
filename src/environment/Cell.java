package environment;

import com.sun.source.tree.ConditionalExpressionTree;
import game.BotPlayer;
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

	public void setPlayer(Player player) throws InterruptedException {
		while(isOcupied()) {
			//System.out.println("Célula de " + position.toString() + " está ocupada pelo jogador " + this.player.toString());
			//		"Jogador "+ player.toString() + " terá de esperar que a posição fique livre.");
			cellFree.await();
		}
		this.player = player;
		//cellOcupied.signalAll();
	}

	public synchronized void movePlayer(Player player, Cell to){
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
			if(p.getCurrentStrength() != -1){
				fight(this.player, p);
				this.player = null;
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
	public void fight(Player player1, Player player2){
		int comparison = Byte.compare(player1.getCurrentStrength(), player2.getCurrentStrength());
		if(comparison > 0){
			fightStrengthChanger(player1, player2);
		}else if(comparison < 0){
			fightStrengthChanger(player2, player1);
		}else{
			double prob = Math.random();
			if(prob > 0.5){
				fightStrengthChanger(player1, player2);
			}else{
				fightStrengthChanger(player2, player1);
			}
		}
	}
	public void fightStrengthChanger(Player winner, Player loser){
		if((winner.getCurrentStrength() + loser.getCurrentStrength()) > 10){
			winner.setStrength((byte)10);
		}else{
			winner.addStrength(loser.getCurrentStrength());
		}
		loser.killPlayer();
	}
	
	

}
