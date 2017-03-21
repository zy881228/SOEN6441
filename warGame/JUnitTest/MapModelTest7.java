package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest7 extends TestCase {

	public WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Map Test7 begins");
		mapModel = new WarGameMapModel("test", 15, 20);
	}

	public void tearDown() throws Exception {
		System.out.println("Map Test7 ends");
		System.out.println();
	}
    
	public void testCanReach(){
		System.out.println("Exit door is not surrounded by the walls");
		String map[][] = mapModel.getMap();
		
		map[0][7] = "O";
		map[1][7] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());

		map[0][7] = "x";
		map[5][map[0].length-1] = "O";
		map[5][map[0].length-2] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());
		
		map[5][map[0].length-1] = "x";
		map[map.length-1][6] = "O";
		map[map.length-2][6] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());
		
		map[map.length-1][6] = "x";
		map[8][0] = "O";
		map[8][1] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());
		
	}
	
	
}
