package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest1 extends TestCase {

	public WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Map Test1 begins");
		mapModel = new WarGameMapModel("test", 15, 20);
	}

	public void tearDown() throws Exception {
		System.out.println("Map Test1 ends");
		System.out.println();
	}
    
	public void testHasEntry(){
		System.out.println("Map contains an entry");
		assertFalse(mapModel.hasEntry());
		String map[][] = mapModel.getMap();
		map[0][5] = "I";
		mapModel.setMap(map);
		assertTrue(mapModel.hasEntry());
	}
	
	
}
