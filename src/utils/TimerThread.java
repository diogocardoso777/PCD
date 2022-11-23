package utils;

public class TimerThread extends Thread{
    private int seconds;

    public TimerThread(int seconds, boolean changeBool){
        this.seconds = seconds;
    }
}
