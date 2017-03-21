package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest6 extends TestCase {

	public WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Map Test6 begins");
		mapModel = new WarGameMapModel("test", 15, 20);
	}

	public void tearDown() throws Exception {
		System.out.println("Map Test6 ends");
		System.out.println();
	}
    
	public void testCanReach(){
		System.out.println("Entry door is not surrounded by the walls");
		String map[][] = mapModel.getMap();
		
		map[0][5] = "I";
		map[1][5] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());

		map[0][5] = "x";
		map[3][map[0].length-1] = "I";
		map[3][map[0].length-2] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());
		
		map[3][map[0].length-1] = "x";
		map[map.length-1][5] = "I";
		map[map.length-2][5] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());
		
		map[map.length-1][5] = "x";
		map[5][0] = "I";
		map[5][1] = "x";
		mapModel.setMap(map);
		assertFalse(mapModel.canReach());
		
	}
	
	
}
