package utils;

import java.util.LinkedList;

public class BlockingQueue<T> {
    private LinkedList<T> list;
    private int maxSize;

    public BlockingQueue() {
        this.list = new LinkedList<>();
        maxSize = -1;
    }

    public BlockingQueue(int size) {
        this.list = new LinkedList<T>();
        this.maxSize = size;
    }

    public synchronized void put (T t) throws InterruptedException {
        if(maxSize != -1) {
            while (list.size() >= maxSize){
                wait();
            }
            list.add(t);
            notifyAll();
        }
    }

    public synchronized T take () throws InterruptedException {
        while (list.size() == 0){
            wait();
        }
        notifyAll();
        return list.removeFirst();
    }

    public synchronized int getSize(){
        return list.size();
    }

    public void clear(){
        list.clear();
    }
}
