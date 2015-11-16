import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.jdom2.*;
 
public class Server {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inStream = null;
    private Deserializer deserializer = null;

 
    public Server() {
 
    }
 
    public void communicate() {
        try {
            serverSocket = new ServerSocket(4321);
            socket = serverSocket.accept();
            System.out.println("Connected");
            inStream = new ObjectInputStream(socket.getInputStream());
 
            String inputString = inStream.readObject();
            SAXBuilder sb = new SAXBuilder();
            Document XMLDoc = sb.build(inputString);
            System.out.println(new XMLOutputter(Format.getPrettyFormat().outputString(XMLDoc)));
            socket.close();
 
        } catch (SocketException se) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        Server server = new Server();
        server.communicate();
    }
}
