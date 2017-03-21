package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest5 extends TestCase {

	public WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Map Test5 begins");
		mapModel = new WarGameMapModel("test", 15, 20);
	}

	public void tearDown() throws Exception {
		System.out.println("Map Test5 ends");
		System.out.println();
	}
    
	public void testElementOnEdge(){
		System.out.println("Elements cannot be set on the border of the map");
		String map[][] = mapModel.getMap();
		
		map[0][5] = "i";
		mapModel.setMap(map);
		assertFalse(mapModel.elementOnEdge());
		
		map[0][5] = "m";
		mapModel.setMap(map);
		assertFalse(mapModel.elementOnEdge());
		
		map[0][5] = "n";
		mapModel.setMap(map);
		assertFalse(mapModel.elementOnEdge());
		
		map[0][5] = "x";
		map[3][3] = "i";
		map[4][4] = "m";
		map[5][5] = "n";
		mapModel.setMap(map);
		assertTrue(mapModel.elementOnEdge());
	}
	
	
}
