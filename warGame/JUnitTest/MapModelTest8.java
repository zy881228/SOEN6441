package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest8 extends TestCase {

	public WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Map Test8 begins");
		mapModel = new WarGameMapModel("test", 15, 20);
	}

	public void tearDown() throws Exception {
		System.out.println("Map Test8 ends");
		System.out.println();
	}
    
	public void testCanReach(){
		System.out.println("Elements are not surrounded by the walls");
		String map[][] = mapModel.getMap();
		
		map[5][5] = "i";
		map[4][5] = "x";
		map[6][5] = "x";
		map[5][4] = "x";
		map[5][6] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());

		map[5][5] = "m";
		map[4][5] = "x";
		map[6][5] = "x";
		map[5][4] = "x";
		map[5][6] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());
		
		map[5][5] = "n";
		map[4][5] = "x";
		map[6][5] = "x";
		map[5][4] = "x";
		map[5][6] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());
		
		map[5][6] = "f";
		mapModel.setMap(map);
		assertTrue(mapModel.canReach());
	}
	
	
}
