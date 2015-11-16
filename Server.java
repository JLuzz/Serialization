import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class Server {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inStream = null;
    private Deserializer deserializer = null;
    private Inspector visualizer = null;

    public Server() {

    }

    public void communicate() {
        try {
            serverSocket = new ServerSocket(4321);
            socket = serverSocket.accept();
            System.out.println("Connected");
            inStream = new ObjectInputStream(socket.getInputStream());

            String inputString = (String) inStream.readObject();
            SAXBuilder sb = new SAXBuilder();
            Document XMLDoc = sb.build(new StringReader(inputString));

            System.out.println("---Recevied XML Document---");
            System.out.println(new XMLOutputter(Format.getPrettyFormat()).outputString(XMLDoc));
            System.out.println("---Derserializing XML Document---");

            deserializer = new Deserializer();
            Object deseriallyObj = (Object) deserializer.deserialize(XMLDoc);

            visualizer = new Inspector();
            visualizer.inspect(deseriallyObj, true);

            socket.close();

        }catch(SocketException se){
            System.exit(0);
        }catch(IOException e){
            e.printStackTrace();
        }catch(ClassNotFoundException cn){
            cn.printStackTrace();
        }catch(JDOMException je){
            je.printStackTrace();
        }catch(Exception ex){
          ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.communicate();
    }
}
