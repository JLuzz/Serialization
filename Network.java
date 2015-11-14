import java.io.OutputStream;
import java.net.Socket;
import java.io.IOException;

public class Network
{
  private Socket socket = null;
  private int port = 4321;
  private String ip = "localhost";

  public void send(String message)
  {
    if(socket == null)
      connect();
    if(socket != null && socket.isConnected())
    {
      try
      {
        OutputStream outstream = socket.getOutputStream();
        outstream.write(message.getBytes());
        outstream.flush();
      }
      catch()
      {

      }
    }
    else
      System.out.println("Socket unavailable");
  }

  public void connect()
  {
    try
    {
      socket = new Socket(ip, port);
      socket.setSoTimeout(500);
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
      sender.connected = false;
    }
  }

  public void disconnect()
  {
    //socket.close() or something like that
  }

  public void setIP(String ip)
  {
    this.ip = ip;
  }
  public void setPort()
  {
  }
}
