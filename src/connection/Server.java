package connection;

import gui.GameGuiMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORTO = 8080;
    private GameGuiMain gameGuiMain;

    public static void main(String[] args) {

        //Game game = new Game();
        try {
            new Server().StartServing();
        }catch (IOException e){

        }
    }
    public void StartServing() throws IOException{
        gameGuiMain = GameGuiMain.getGameGuiInstance();

        ServerSocket ss = new ServerSocket(PORTO);
        try{
            while(true){
                Socket socket = ss.accept();
                System.out.println("Got a client");
            }
        }
        finally {
            ss.close();
        }
    }
}
