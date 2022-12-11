package Client;

import game.Player;
import gui.GameGuiMain;
import jdk.swing.interop.SwingInterOpUtils;
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

    private void receiveAndSendMessage() throws IOException, ClassNotFoundException{
        while (!stateInfo.gameIsFinished()){
            stateInfo = (GameStateInfo) in.readObject();
            sendKey();
            notifyChange();
        }
        System.out.println(stateInfo.gameIsFinished());
    }

    private void sendKey(){
        if (clientGui.getBoard().getLastPressedDirection() != null) {
            out.println(clientGui.getBoard().getLastPressedDirection().toString());
            clientGui.getBoard().clearLastPressedDirection();
        }
    }
    public void printResults(){
        if(stateInfo.isWinner()){
            System.out.println("Ganhaste");
        }else{
            System.out.println("Perdeste");
        }
    }

    public GameStateInfo getStateInfo() {
        return stateInfo;
    }

    public void notifyChange() {
        setChanged();
        notifyObservers();
    }
    public void runClient(String address, int port, boolean alternativeKeys){
        try{
            connectToServer(address, port);
            stateInfo = (GameStateInfo) in.readObject();
            clientGui = new ClientGui(stateInfo.getCells().length, stateInfo.getCells()[0].length,this, alternativeKeys);
            clientGui.init();
            receiveAndSendMessage();
            printResults();

        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Perdeste");
        } finally {
            try{
                if(socket!= null)
                    this.socket.close();
            }catch (IOException e) {//...
                throw new RuntimeException(e);
            }
        }
    }




    public static void main(String[] args) {

        new Client().runClient(
                args[0],
                Integer.parseInt(args[1]),
                Boolean.parseBoolean(args[2])
        );
        System.exit(0);
    }

}
