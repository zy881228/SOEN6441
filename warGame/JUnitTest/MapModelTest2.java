package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest2 extends TestCase {

	public WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Map Test2 begins");
		mapModel = new WarGameMapModel("test", 15, 20);
	}

	public void tearDown() throws Exception {
		System.out.println("Map Test2 ends");
		System.out.println();
	}
    
	public void testHasExit(){
		System.out.println("Map contains an exit");
		assertFalse(mapModel.hasExit());
		String map[][] = mapModel.getMap();
		map[0][5] = "O";
		mapModel.setMap(map);
		assertTrue(mapModel.hasExit());
	}
	
	
}
