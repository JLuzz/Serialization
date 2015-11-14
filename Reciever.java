impot org.jdom2.Document;

public class Reciever
{
  private static Deserializer deserializer = null;
  private static Visualizer visualizer = null;
  private static SocketAcceptor socketAcceptor= null;

  public static boolean connected;

  public static void main(String[] args)
  {
    initialize(args);

    while(connected)
    {
        String message = socketAcceptor.getMessage();

        
    }
  }
}
