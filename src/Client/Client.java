package Client;

import gui.GameGuiMain;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

public class Client {
    private ObjectInputStream in;
    private PrintWriter out;
    private Socket socket;
    public static final int PORTO = 8080;

    //private GameGuiMain gameGuiMain;

    public Client() throws IOException {
    }

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
        while (true){
            byte[][] infoReceived = (byte[][]) in.readObject();
            System.out.println(infoReceived);
        }
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
