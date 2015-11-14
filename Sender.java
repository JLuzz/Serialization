public class Sender
{
  private static Network network = null;
  private static Serializer serializer = null;
  private static ObjectCreator objCreator = null;

  private static boolean connected;

  public staic void main(String[] args)
  {
      initialize(args);

      while(connected)
      {
        Object obj = objCreator.createObject();

        if(!connected)
          continue;
        Document doc = serializer.serialize(obj);
        String XMLString = XMLtoString(doc);

        network.send(XMLString);
      }
      exit();
  }

  public static void initialize(String[] args)
  {
    connected = true;
    network = new Network();
    serializer = new Serializer();
    objCreator = new ObjectCreator();

    if(args.length == 2)
    {
      network.setIP(args[0]);
      network.setPort(Integer.valueOf(args[1]));
    }
  }

  public static String XMLtoString(Document doc)
  {
    XMLOutputter xmlOutput = new XMLOutputter();
    return xmlOutput.outputString();
  }

  public static void exit()
  {
    network.disconnect();
    connected = false;
  }
}
