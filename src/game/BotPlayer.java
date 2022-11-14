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
        System.out.println(getIdentification());
        game.addPlayerToGame(this);

        while(super.getCurrentStrength()!= 0){
            try {
                sleep(Game.REFRESH_INTERVAL);
            } catch (InterruptedException e) {
                return;
            }
            double prob = Math.random();

            if(prob < 0.25){
                move(Direction.DOWN);
            }else if(prob < 0.5){
                move(Direction.LEFT);
            }else if(prob < 0.75){
                move(Direction.UP);
            }else{
                move(Direction.RIGHT);
            }


        }


        //run method
    }
}
