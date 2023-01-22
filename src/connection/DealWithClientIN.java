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

    private boolean isFinished(Player p){
        if(game.isAlive(playerId))
            return false;
        else if(game.isWinner(playerId))
            game.playerWin();

        return true;
    }

    @Override
    public void run() {
        try {
            doConnections(socket);
            Player p = game.getPlayerFromId(playerId);
            while (!isFinished(p) && !isInterrupted()){
                p = game.getPlayerFromId(playerId);
                sleep(Game.REFRESH_INTERVAL);
                String keyPressed = in.readLine();
                p.move(Direction.directionFor(keyPressed));
            }

        } catch (IOException | InterruptedException e) {
            try {
                System.out.println("dealwithclientIN socket close");
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }



}
