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
		ObjectCreator.SimpleObject sObject = objCreate.new SimpleObject(2, 3);
		assertNotNull(sObject);
	}

}
