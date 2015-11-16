import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

public class TestObjectCreator{

	private static ObjectCreator objCreate;

	@BeforeClass
	public static void oneTimeSetup(){
		objCreate = new ObjectCreator();
	}

	@Before
	public void setUp(){

	}

	@AfterClass
	public static void oneTimeTearDown(){
			objCreate = null;
	}

	@After
	public void tearDown(){

	}

	@Test
	public void testCreateSimpleObject(){
		SimpleObject sObject = new SimpleObject(2, 3);
		assertNotNull(sObject);
	}

	@Test
	public void testCreateObjectReferenceObjects(){
		SimpleObject sObject1 = new SimpleObject(2,3);
		SimpleObject sObject2 = new SimpleObject(3,4);
		ObjectReferenceObjects OROObject = new ObjectReferenceObjects(sObject1, sObject2);
		assertNotNull(OROObject);
	}

}
