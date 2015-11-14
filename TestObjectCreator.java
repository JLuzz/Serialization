import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.inputStream;

public class TestObjectCreator{

	private objCreate;

	@BeforeClass
	public staic void oneTimeSetup(){
		objCreate = new ObjectCreator();
	}

	@Before
	public static void setUp(){

	}

	@AfterClass
	public static void oneTimeTearDown(){
			objCreate = null;
	}

	@After
	public static void tearDown(){
		System.setIn(System.in);
	}

	@Test
	public testCreateSimpleObject(){
		sObject = objCreate.createSimpleObject();
		ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
		System.setIn(in);
		//in.reset();
		in.read("3".getBytes());

	}

}
