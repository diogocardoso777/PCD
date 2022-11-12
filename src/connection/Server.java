package connection;

import game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORTO = 8080;

    public static void main(String[] args) {
        Game game = new Game();
    }
    public void StartServing() throws IOException{
        ServerSocket ss = new ServerSocket(PORTO);
        try{
            while(true){
                Socket socket = ss.accept();
            }
        }
        finally {
            ss.close();
        }
    }
}
