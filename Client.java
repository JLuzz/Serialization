import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class Client {
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private boolean isConnected = false;
    private Serializer serializer = null;
    private ObjectCreator objCreate = null;
    private Document XMLDoc = null;

    public Client(){
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
                try{
                  XMLDoc = serializer.serialize(serialObject);
                }catch(Exception e){}

                System.out.println(new XMLOutputter(Format.getPrettyFormat()).outputString(XMLDoc));

                outputStream.writeObject(new XMLOutputter(Format.getPrettyFormat()).outputString(XMLDoc));

            }catch(SocketException se){
                se.printStackTrace();
                System.exit(0);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Boolean havingFun = true;
        Client client = new Client();
        while(havingFun){
          //System.out.println("Wanna serialize an object cool guy?(y/n)");
          //replace with finding user input
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
