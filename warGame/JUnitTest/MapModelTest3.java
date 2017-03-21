package warGame.JUnitTest;
import warGame.Model.WarGameMapModel;
import junit.framework.TestCase;


public class MapModelTest3 extends TestCase {

	public WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Map Test3 begins");
		mapModel = new WarGameMapModel("test", 15, 20);
	}

	public void tearDown() throws Exception {
		System.out.println("Map Test3 ends");
		System.out.println();
	}
    
	public void testDoorPosition(){
		System.out.println("Entry door is not in the conner");
		String map[][] = mapModel.getMap();
		
		map[0][0] = "I";
		mapModel.setMap(map);
		assertFalse(mapModel.doorPostion());
		map[0][0] = "x";
		map[0][3] = "I";
		mapModel.setMap(map);
		assertTrue(mapModel.doorPostion());
		
		map[0][map[0].length-1] = "I";
		mapModel.setMap(map);
		assertFalse(mapModel.doorPostion());
		map[0][map[0].length-1] = "x";
		map[0][3] = "I";
		mapModel.setMap(map);
		assertTrue(mapModel.doorPostion());

		map[map.length-1][0] = "I";
		mapModel.setMap(map);
		assertFalse(mapModel.doorPostion());
		map[map.length-1][0] = "x";
		map[0][3] = "I";
		mapModel.setMap(map);
		assertTrue(mapModel.doorPostion());
		
		map[map.length-1][map[0].length-1] = "I";
		mapModel.setMap(map);
		assertFalse(mapModel.doorPostion());
		map[map.length-1][map[0].length-1] = "x";
		map[0][3] = "I";
		mapModel.setMap(map);
		assertTrue(mapModel.doorPostion());

	}
	
	
}
