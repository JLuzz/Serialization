import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.jdom2.*;

public class TestSerializer{

	private static Serializer serially;
  private static ObjectCreator objCreate;
  private Document expectedDoc;
  private Element root;

	@BeforeClass
	public static void oneTimeSetup(){
		serially = new Serializer();
    objCreate = new ObjectCreator();
	}

	@Before
	public void setUp(){
    expectedDoc = new Document();
    root = new Element("serialized");
    expectedDoc.setRootElement(root);
	}

	@AfterClass
	public static void oneTimeTearDown(){
			serially = null;
	}

	@After
	public void tearDown(){
    expectedDoc = null;
    root = null;
	}

	@Test
	public void testSerializeSimpleObject(){
    //serialize the test object
    ObjectCreator.SimpleObject sObject = objCreate.new SimpleObject(2, 3);
    Document actualDoc = serially.serialize(sObject);

    if (expectedDoc == null)
      System.out.println("expected fail");
    if (actualDoc == null)
      System.out.println("actualDoc fail");

    //create a document of expexted values to compare against
    Element objectElement = new Element("object");
    objectElement.setAttribute(new Attribute("class", "SimpleObject"));
    objectElement.setAttribute(new Attribute("id","0"));

    Element elementA = new Element("field");
    elementA.setAttribute(new Attribute("name", "a"));
    elementA.setAttribute(new Attribute("declaringClass", "SimpleObject"));
    Element valueA = new Element("value");
    valueA.setText("2");
    elementA.addContent(valueA);
    objectElement.addContent(elementA);

    Element elementB = new Element("field");
    elementB.setAttribute(new Attribute("name", "a"));
    elementB.setAttribute(new Attribute("declaringClass", "SimpleObject"));
    Element valueB = new Element("value");
    valueB.setText("3");
    elementB.addContent(valueB);
    objectElement.addContent(elementB);

    expectedDoc.getRootElement().addContent(objectElement);

    assertEquals(expectedDoc.toString(), actualDoc.toString());
	}

}
