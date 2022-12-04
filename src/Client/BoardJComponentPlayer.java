package Client;

import environment.Coordinate;
import environment.Direction;
import game.Game;
import game.Player;
import utils.GameStateInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Creates a JComponent to display the game state.
 * At the same time, this is also a KeyListener for itself: when a key is pressed,
 * attribute lastPressedDirection is updated accordingly. This feature is a demo to
 * better understand how to deal with keys pressed, useful for the remote client.
 * This feature is not helpful for the main application and should be ignored.
 * This class does not need to be edited.
 * @author luismota
 *
 */
public class BoardJComponentPlayer extends JComponent implements KeyListener {

	private Image obstacleImage = new ImageIcon("obstacle.png").getImage();
	private Image humanPlayerImage= new ImageIcon("abstract-user-flat.png").getImage();
	private Direction lastPressedDirection=null;
	private int DIMX;
	private int DIMY;
	private GameStateInfo state;
	public BoardJComponentPlayer(int DIMX, int DIMY, GameStateInfo state) {
		setFocusable(true);
		addKeyListener(this);
		this.DIMX = DIMX;
		this.DIMY = DIMY;
		this.state = state;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double cellWidth=getWidth()/(double)DIMX;
		double cellHeight=getHeight()/(double)DIMY;

		for (int y = 1; y < Game.DIMY; y++) {
			g.drawLine(0, (int)(y * cellHeight), getWidth(), (int)(y* cellHeight));
		}
		for (int x = 1; x < Game.DIMX; x++) {
			g.drawLine( (int)(x * cellWidth),0, (int)(x* cellWidth), getHeight());
		}
		for (int x = 0; x < DIMX; x++)
			for (int y = 0; y < DIMY; y++) {
				Coordinate p = new Coordinate(x, y);
				if (state.getCells()[x][y] != null) {
					int strength = state.getCells()[x][y].getStrength();
					boolean isHuman = state.getCells()[x][y].isHuman();
					// Fill yellow if there is a dead player
					if (strength == 0) {
						g.setColor(Color.YELLOW);
						g.fillRect((int) (p.x * cellWidth),
								(int) (p.y * cellHeight),
								(int) (cellWidth), (int) (cellHeight));
						g.drawImage(obstacleImage, (int) (p.x * cellWidth), (int) (p.y * cellHeight),
								(int) (cellWidth), (int) (cellHeight), null);
						// if player is dead, don'd draw anything else?
						continue;
					}
					// Fill green if it is a human player
					if (isHuman) {
						g.setColor(Color.GREEN);
						g.fillRect((int) (p.x * cellWidth),
								(int) (p.y * cellHeight),
								(int) (cellWidth), (int) (cellHeight));
						// Custom icon?
						g.drawImage(humanPlayerImage, (int) (p.x * cellWidth), (int) (p.y * cellHeight),
								(int) (cellWidth), (int) (cellHeight), null);
					}
					//g.setColor(new Color(player.getIdentification() * 1000));
					((Graphics2D) g).setStroke(new BasicStroke(5));
					Font font = g.getFont().deriveFont((float) cellHeight);
					g.setFont(font);
					String strengthMarking = (strength == 10 ? "X" : "" + strength);
					g.drawString(strengthMarking,
							(int) ((p.x + .2) * cellWidth),
							(int) ((p.y + .9) * cellHeight));

				}
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT :
			lastPressedDirection= Direction.LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			lastPressedDirection= Direction.RIGHT;
			break;
		case KeyEvent.VK_UP:
			lastPressedDirection= Direction.UP;
			break;
		case KeyEvent.VK_DOWN:
			lastPressedDirection= Direction.DOWN;
			break;
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		//ignore
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Ignored...
	}

	public Direction getLastPressedDirection() {
		return lastPressedDirection;
	}

	public void clearLastPressedDirection() {
		lastPressedDirection=null;
	}
}
