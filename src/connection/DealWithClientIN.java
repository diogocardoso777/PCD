package connection;

import environment.Direction;
import game.Game;
import game.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DealWithClientIN extends Thread{
    private Socket socket;
    private BufferedReader in;
    private int playerId;
    private Game game;

    public DealWithClientIN(Socket socket, Game game, int id){
        this.socket = socket;
        this.game = game;
        this.playerId = id;
    }
    private void doConnections(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
    }

    public void run() {
        try {
            doConnections(socket);
            Player p = game.getPlayerFromId(playerId);
            while (!isFinished(p)){
                sleep(Game.REFRESH_INTERVAL);
                String keyPressed = in.readLine();
                game.getPlayerFromId(playerId).move(Direction.directionFor(keyPressed));
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isFinished(Player p){
        if(p.getCurrentStrength()!= 0 && p.getCurrentStrength() < 10)
            return false;
        else if(p.getCurrentStrength() >= 10)
            game.playerWin(playerId);

        return true;
    }

}
