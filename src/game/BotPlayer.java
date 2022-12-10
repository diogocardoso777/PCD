package game;

import environment.Direction;

import static java.lang.Thread.sleep;

public class BotPlayer extends Player implements Runnable{
    public BotPlayer(int id, Game game) {
        super(id, game);
    }

    @Override
    public boolean isHumanPlayer() {
        return false;
    }

    @Override
    public void run() {
        game.addPlayerToGame(this);

        while(getCurrentStrength()!= 0 && getCurrentStrength() < 10){
            try {
                sleep(originalStrength * Game.REFRESH_INTERVAL);
            } catch (InterruptedException e) {
                return;
            }
            move(Direction.random());
        }

        if(super.getCurrentStrength() >= 10) {
            System.out.println("Thread nr. " + getIdentification() + " has won");
            game.playerWin(getIdentification());
        }
    }
}
