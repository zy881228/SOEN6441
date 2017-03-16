package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest1 extends TestCase {

	public WarGameMapModel mapModel = new WarGameMapModel();
	
	public void setUp() throws Exception {
		System.out.println("Test1 begins");
		//mapModel.createMap("Test Map", 8, 8);
	}

	public void tearDown() throws Exception {
		System.out.println("Test1 ends");
		System.out.println();
	}
    /*
	public void testHasEntry(){
		System.out.println("Map contains an entry");
		assertFalse(mapModel.hasEntry());
		//mapModel.setElements(4, 4, "I");
		assertTrue(mapModel.hasEntry());
		
	}
	
	public void testHasExit(){
		System.out.println("Map contains an exit");
		assertFalse(mapModel.hasExit());
		//mapModel.setElements(3, 3, "O");
		assertTrue(mapModel.hasExit());
	}		*/
	
	
}
