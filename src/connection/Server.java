package connection;

import game.PhoneyHumanPlayer;
import gui.GameGuiMain;
import utils.TimerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Server {
    public static final int PORTO = 8080;
    private GameGuiMain gameGuiMain;
    private ServerSocket ss;
    private Boolean waitingForClients;

    public static void main(String[] args) {

        //Game game = new Game();
        try {
            new Server().startServing();


        }catch (IOException e){

        }
    }
    public void startServing() throws IOException{
        waitingForClients = true;
        gameGuiMain = GameGuiMain.getGameGuiInstance();
        gameGuiMain.getGame().createBotThreads();
        System.out.println("Threads Created");
        ss = new ServerSocket(PORTO);
        TimerThread tt = new TimerThread(gameGuiMain.getGame().INITIAL_WAITING_TIME, this);
        tt.start();
        try{
            while(waitingForClients){
                System.out.println("Sleeping");
                Socket socket = ss.accept();
                System.out.println("Got a client");
                int id = gameGuiMain.getGame().numPlayers();
                //System.out.println("SERVER ID "+ id);
                gameGuiMain.getGame().addPlayerToGame(new PhoneyHumanPlayer(id, gameGuiMain.getGame()));
                DealWithClient dealWithClient = new DealWithClient(socket, gameGuiMain.getGame(), id);
                DealWithClientIN dealWithClientIN = new DealWithClientIN(socket,gameGuiMain.getGame(), id);
                dealWithClientIN.start();
                //System.out.println(gameGuiMain.getGame().getPlayerFromId(id));
                //System.out.println("thread is alive = " + dealWithClientIN.isAlive());
                gameGuiMain.getGame().addPlayerThread(dealWithClient);
                sleep(100);

            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            threadHandler();
        }
    }
    public void threadHandler(){
        System.out.println("Done Waiting for Player to Connect");
        gameGuiMain.getGame().runThreads();
        System.out.println("Threads Running");
        System.out.println("MainWaiting");
        gameGuiMain.getGame().waitingToFinish();
    }

    public void close(){
        try {
            System.out.println("Closing");
            waitingForClients = false;
            ss.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
