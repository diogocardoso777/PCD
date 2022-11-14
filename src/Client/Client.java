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
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket = new Socket((InetAddress.getByName(null)), PORTO);
    public static final int PORTO = 8080;

    private GameGuiMain gameGuiMain;

    public Client() throws IOException {
    }

    public void connectToServer() throws IOException {
        InetAddress address = InetAddress.getByName(null);
        System.out.println("Endereço: " + address);
        //this.socket = new Socket(address, PORTO);
        System.out.println("Socket:" + socket);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())),
                true);
    }
    public void runClient(){
        try{
            connectToServer();
            sleep(1000);
        }catch (IOException e) {// ERRO...
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try{
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
