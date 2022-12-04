package utils;

import connection.Server;
import game.Player;

import java.io.IOException;
import java.util.function.Function;

public class TimerThread extends Thread{
    private Server server;
    private int seconds;
    private Thread thread;


    public TimerThread(int seconds, Server server){
        this.server = server;
        this.seconds = seconds;
    }
    public TimerThread(int seconds, Thread thread){
        this.seconds = seconds;
        this.thread = thread;
    }

    @Override
    public void run() {
        try {
            sleep(seconds);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(server!= null){
            server.close();
        }else{
            thread.interrupt();
        }
    }
}
