package game;


import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import environment.Cell;
import environment.Coordinate;
import utils.CountDownLatch;

public class Game extends Observable {

	public static final int DIMY = 30;
	public static final int DIMX = 30;
	private static final int NUM_PLAYERS = 90;				//porque private??
	private static final int NUM_FINISHED_PLAYERS_TO_END_GAME=3;

	public static final long REFRESH_INTERVAL = 200;
	public static final double MAX_INITIAL_STRENGTH = 3;
	public static final long MAX_WAITING_TIME_FOR_MOVE = 2000;
	public static final long INITIAL_WAITING_TIME = 6000;
	public CountDownLatch countDownLatch = new CountDownLatch(NUM_FINISHED_PLAYERS_TO_END_GAME);


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
			throw new RuntimeException(e);
		}
		// To update GUI
		notifyChange();
	}

	public Cell getCell(Coordinate at) {
		return board[at.x][at.y];
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

	public void playerWin(){
		countDownLatch.countDown();
	}

	public void runThreads (){
		Thread[] botPlayers = new Thread[NUM_PLAYERS];
		for (int i = 0; i < NUM_PLAYERS; i++) {
			botPlayers[i] = new Thread(new BotPlayer(i, this));
			botPlayers[i].start();
		}
		try {
			wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}


		//BotPlayer[] bots = new BotPlayer[game.NUM_PLAYERS];
	/*	BotPlayer p1 = new BotPlayer(1, game);
		BotPlayer p2 = new BotPlayer(2, game);
		BotPlayer p3 = new BotPlayer(3, game);
		BotPlayer p4 = new BotPlayer(4, game);
		BotPlayer p5 = new BotPlayer(5, game);
		BotPlayer p6 = new BotPlayer(6, game);
		BotPlayer p7 = new BotPlayer(7, game);
		BotPlayer p8 = new BotPlayer(8, game);
		BotPlayer p9 = new BotPlayer(9, game);
	/*	BotPlayer p10 = new BotPlayer(10, game);
		BotPlayer p11 = new BotPlayer(11, game);
		BotPlayer p12 = new BotPlayer(12, game);
		BotPlayer p13 = new BotPlayer(12, game);*/

	/*	Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		Thread t3 = new Thread(p3);
		Thread t4 = new Thread(p4);
		Thread t5 = new Thread(p5);
		Thread t6 = new Thread(p6);
		Thread t7 = new Thread(p7);
		Thread t8 = new Thread(p8);
		Thread t9 = new Thread(p9);
	/*	Thread t10 = new Thread(p10);
		Thread t11 = new Thread(p11);
		Thread t12 = new Thread(p12);
		Thread t13 = new Thread(p13);



		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();
	/*	t10.start();
		t11.start();
		t12.start();
		t13.start();

	 */
	}
}
