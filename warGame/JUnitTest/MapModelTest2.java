package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest2 extends TestCase {

	public WarGameMapModel mapModel = new WarGameMapModel();
	/*
	public void setUp() throws Exception {
		System.out.println("Test2 begins");
		mapModel.createMap("Test Map", 8, 8);
	}
	
	public void tearDown() throws Exception {
		System.out.println("Test2 ends");
		System.out.println();
	}
	
	public void testElementOnEdge(){
		System.out.println("Item, character cannot be set on the border of the map");
		mapModel.setElements(1, 4, "i");
		assertFalse(mapModel.elementOnEdge());
		mapModel.setElements(1, 4, "x");
		mapModel.setElements(2, 4, "i");
		assertTrue(mapModel.elementOnEdge());

	}
	*/
}