package Client;

import game.Player;
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
    private ClientGui clientGui;

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

    private void waitAndSendMessage() throws IOException, ClassNotFoundException{
        stateInfo = (GameStateInfo) in.readObject();
        clientGui = new ClientGui(stateInfo.getCells().length, stateInfo.getCells()[0].length,this);
        clientGui.init();
        while (true){
            stateInfo = (GameStateInfo) in.readObject();
            sendKey();
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
            waitAndSendMessage();

        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
            //System.out.println("Jogo terminou");
        } finally {
            try{
                if(socket!= null)
                    this.socket.close();
            }catch (IOException e) {//...
                throw new RuntimeException(e);
            }
        }
    }

    private void sendKey(){
        if (clientGui.getBoard().getLastPressedDirection() != null) {
            //System.out.println(clientGui.getBoard().getLastPressedDirection().toString());
            out.println(clientGui.getBoard().getLastPressedDirection().toString());
            clientGui.getBoard().clearLastPressedDirection();
        }
    }


    public static void main(String[] args) throws IOException {
        new Client().runClient();
    }

}
