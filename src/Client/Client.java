package Client;

import gui.GameGuiMain;
import utils.GameStateInfo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import static java.lang.Thread.sleep;

public class Client extends Observable {
    private ObjectInputStream in;
    private PrintWriter out;
    private Socket socket;
    public static final int PORTO = 8080;
    private GameStateInfo stateInfo;
    //private GameGuiMain gameGuiMain;

    public void connectToServer() throws IOException {
        InetAddress address = InetAddress.getByName(null);
        System.out.println("Endereço: " + address);

        this.socket = new Socket(address, PORTO);
        System.out.println("Socket:" + socket);
        in = new ObjectInputStream(socket.getInputStream());
        out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())),
                true);
    }

    private void waitingMessage() throws IOException, ClassNotFoundException {

        stateInfo = (GameStateInfo) in.readObject();
        ClientGui clientGui = new ClientGui(stateInfo.getCells().length, stateInfo.getCells()[0].length, stateInfo, this);
        clientGui.init();
        while (true){
            stateInfo = (GameStateInfo) in.readObject();
            clientGui.updateGui();
           // System.out.println(stateInfo);
            notifyChange();
        }
    }

    public GameStateInfo getStateInfo() {
        return stateInfo;
    }

    public void notifyChange() {
        setChanged();
        notifyObservers();
    }
    public void runClient(){
        try{
            connectToServer();
            waitingMessage();
        }catch (IOException e) {// ERRO...
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(socket!= null)
                this.socket.close();
            }catch (IOException e) {//...
                throw new RuntimeException(e);
            }
        }
    }
    void sendKey(){

    }

    public static void main(String[] args) throws IOException {
        new Client().runClient();

    }


}
//criar um package para o client e meter la o client
