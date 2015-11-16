import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.jdom2.*;
 
public class Client {
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private boolean isConnected = false;
    private Serializer serializer = null;
    private ObjectCreator objCreate = null;
 
    public Client() {
 
    }
 
    public void communicate() {
 
        while (!isConnected) {
            try {
                socket = new Socket("localHost", 4321);
                System.out.println("Connected to Server");
                isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());

                //do work
                serializer = new Serializer();
                objCreate = new ObjectCreator();
                Object serialObject = objCreate.createObject();
                Document XMLDoc = serializer.serialize(serialObject);

                System.out.println(new XMLOutputter(Format.getPrettyFormat().outputString(XMLDoc)));

                outputStream.writeObject(new XMLOutputter(Format.getPrettyFormat().outputString(XMLDoc)));
 
 
            } catch (SocketException se) {
                se.printStackTrace();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    public static void main(String[] args){
        Boolean havingFun = true;
        Client client = new Client();
        while(havingFun){
          System.out.println("Wanna serialize an object cool guy?(y/n)");
          String userIn = "y";
          if(userIn.equals("y")){
            client.communicate();
            havingFun = false;
          }else{
            havingFun = false;
          }
        }
    }
}
