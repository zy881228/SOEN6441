package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest3 extends TestCase {

	public WarGameMapModel mapModel = new WarGameMapModel();
	/*
	public void setUp() throws Exception {
		System.out.println("Test3 begins");
		mapModel.createMap("Test Map", 8, 8);
	}
	
	public void tearDown() throws Exception {
		System.out.println("Test3 ends");
		System.out.println();
	}
	
	public void testDoorPos(){
		System.out.println("Doors cannot be set in the corner of the map");
		mapModel.setElements(1, 1, "I");
		assertFalse(mapModel.doorPos());
		
		mapModel.setElements(1, 1, "O");
		assertFalse(mapModel.doorPos());

		mapModel.setElements(1, 1, "x");
		mapModel.setElements(1, 4, "I");
		mapModel.setElements(8, 4, "O");
		assertTrue(mapModel.doorPos());

		
	}*/
	
}