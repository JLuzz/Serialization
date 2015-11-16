import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class TestSerializer{

	private static Serializer serially;
  private static ObjectCreator objCreate;
  private Document expectedDoc;
  private Element root;

	@BeforeClass
	public static void oneTimeSetup(){
    objCreate = new ObjectCreator();
	}

	@Before
	public void setUp(){
		serially = new Serializer();
    expectedDoc = new Document();
    root = new Element("serialized");
    expectedDoc.setRootElement(root);
	}

	@AfterClass
	public static void oneTimeTearDown(){
			objCreate = null;
	}

	@After
	public void tearDown(){
		serially = null;
    expectedDoc = null;
    root = null;
	}

	@Test
	public void testSerializeSimpleObject(){
    //serialize the test object
    SimpleObject sObject = new SimpleObject(2, 3);
    Document actualDoc = serially.serialize(sObject);

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

  @Test
  public void testSerializeObjectReferenceObjects(){
    //serialize the test object
    SimpleObject objA = new SimpleObject(1,2);
    SimpleObject objB = new SimpleObject(3,4);
    ObjectReferenceObjects OROObject = new ObjectReferenceObjects(objA, objB);
    Document actualDoc = serially.serialize(OROObject);

    //left off here print XML DOC
		System.out.println(new XMLOutputter(Format.getPrettyFormat()).outputString(actualDoc));

    assertTrue(true);
  }
}
