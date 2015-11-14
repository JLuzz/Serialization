

public class Deserializer
{

  public Document stringToDoc(String documentString)
  {
    Document doc = null;

    try
    {
      SAXBuilder docBuilder = new SAXBuilder();
      InputStream docStream = new ByteArrayInputStream(documentString.getBytes("UTF-8"));
      doc = docBuilder.build(docStream);
    }
    catch(IOException e){

    }
    catch(JDOMException e1){

    }
    return doc;
  }

  private void initializeRefernceMap(Document doc) throws ClassNotFoundException, InstantiationException, IllegalAccessException, DataConverisonException{};
}
