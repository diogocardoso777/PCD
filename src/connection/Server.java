package connection;

import gui.GameGuiMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Server {
    public static final int PORTO = 8080;
    private GameGuiMain gameGuiMain;
    private static int TIME_TO_CONNECT = 5000;


    public static void main(String[] args) {

        //Game game = new Game();
        try {
            new Server().startServing();
        }catch (IOException e){

        }
    }
    public void startServing() throws IOException{
        gameGuiMain = GameGuiMain.getGameGuiInstance();
        gameGuiMain.getGame().createBotThreads();
        System.out.println("Threads Created");
        long t= System.currentTimeMillis();
        long end = t+TIME_TO_CONNECT;
        ServerSocket ss = new ServerSocket(PORTO);
        try{
            while(System.currentTimeMillis() < end){
                System.out.println("Sleeping");
                Socket socket = ss.accept();
                DealWithClient dealWithClient = new DealWithClient(socket, gameGuiMain.getGame());
                dealWithClient.start();
                sleep(100);

            }
            System.out.println("Done Waiting for Player to Connect");
            gameGuiMain.getGame().runThreads();
            System.out.println("Threads Running");
            System.out.println("MainWaiting");
            gameGuiMain.getGame().waitingToFinish();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {


            ss.close();
        }
    }
}
