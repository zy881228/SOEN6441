package warGame;
import junit.framework.TestCase;


public class MapModelTest4 extends TestCase {

	public WarGameMapModel mapModel = new WarGameMapModel();
	
	public void setUp() throws Exception {
		System.out.println("Test4 begins");
		mapModel.createMap("Test Map", 8, 8);
	}
	
	public void tearDown() throws Exception {
		System.out.println("Test4 ends");
		System.out.println();
	}
	
	public void testCanReach(){
		System.out.println("Door/Item/Character cannot be surrounded by the walls");
		
		mapModel.setElements(1, 4, "I");
		mapModel.setElements(2, 4, "x");
		assertFalse(mapModel.canReach());
		
		mapModel.setElements(2, 4, "f");
		assertTrue(mapModel.canReach());

		mapModel.setElements(3, 3, "i");
		mapModel.setElements(3, 2, "x");
		mapModel.setElements(3, 4, "x");
		mapModel.setElements(2, 3, "x");
		mapModel.setElements(4, 3, "x");
		assertFalse(mapModel.canReach());

		
	}
	
}