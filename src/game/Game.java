package game;


import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import environment.Cell;
import environment.Coordinate;
import gui.WinDialog;
import utils.CountDownLatch;

public class Game extends Observable {

	public static final int DIMY = 30;
	public static final int DIMX = 30;
	private static final int NUM_PLAYERS = 90;
	private static final int NUM_FINISHED_PLAYERS_TO_END_GAME=3;

	public static final long REFRESH_INTERVAL = 400;
	public static final double MAX_INITIAL_STRENGTH = 3;
	public static final long MAX_WAITING_TIME_FOR_MOVE = 2000;
	public static final int INITIAL_WAITING_TIME = 6000;
	private CountDownLatch countDownLatch = new CountDownLatch(NUM_FINISHED_PLAYERS_TO_END_GAME);
	private ArrayList<Integer> winners= new ArrayList<Integer>();
	private ArrayList<Thread> players = new ArrayList<>();

	protected Cell[][] board;

	public Game() {
		board = new Cell[Game.DIMX][Game.DIMY];

		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++) 
				board[x][y] = new Cell(new Coordinate(x, y),this	);
	}
	
	/** 
	 * @param player 
	 */
	public void addPlayerToGame (Player player) {
		Cell initialPos=getRandomCell();
		try {
			initialPos.setPlayerInitially(player);
		} catch (InterruptedException e) {
			return;
			//throw new RuntimeException(e);
		}
		// To update GUI
		notifyChange();
	}

	public Cell getCell(Coordinate at) {
		return board[at.x][at.y];
	}

	public Cell [][] getBoard(){
		return board;
	}

	/**	
	 * Updates GUI. Should be called anytime the game state changes
	 */
	public void notifyChange() {
		setChanged();
		notifyObservers();
	}

	public Cell getRandomCell() {
		Cell newCell=getCell(new Coordinate((int)(Math.random()*Game.DIMX),(int)(Math.random()*Game.DIMY)));
		return newCell; 
	}

	public void playerWin(int id){
		winners.add(id);
		countDownLatch.countDown();
	}

	public void createBotThreads (){
		for (int i = 0; i < NUM_PLAYERS; i++)
			players.add(new Thread(new BotPlayer(i, this)));
	}

	public int numPlayers(){
		return players.size();
	}

	public void runThreads(){
		for(Thread t : players)
			t.start();
	}

	public void addPlayerThread(Thread player){
		players.add(player);
	}

	public void waitingToFinish(){
		try {
			countDownLatch.await();
			notifyChange();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		for (Thread b : players)
			if(b.isAlive())
				b.interrupt();
		WinDialog dialog = new WinDialog(winners);
	}

	public Player getPlayerFromId(int id){
		for (int x = 0; x < Game.DIMX; x++)
			for (int y = 0; y < Game.DIMY; y++) {
				Player p = board[x][y].getPlayer();
				if (p != null && p.getIdentification() == id && p.isHumanPlayer())
					return p;
			}
		return null;
	}

}
