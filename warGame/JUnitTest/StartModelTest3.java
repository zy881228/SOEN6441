package warGame.JUnitTest;

import java.util.ArrayList;

import junit.framework.TestCase;
import warGame.Model.WarGameMapModel;
import warGame.Model.WarGameStartModel;

public class StartModelTest3 extends TestCase{

	WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Start Test3 begins");
		mapModel = new WarGameMapModel("test", 10, 10);
	}
	
	public void tearDown() throws Exception {
		System.out.println("Start Test3 ends");
		System.out.println();
	}
	
	public void testcharacterMovement(){
		System.out.println("Character moving to left");
		String map[][] = mapModel.getMap();
		map[5][5] = "h";
		mapModel.setMap(map);
		ArrayList<WarGameMapModel> mapModelLists = new ArrayList<WarGameMapModel>();
		mapModelLists.add(mapModel);
		WarGameStartModel.characterMovement(mapModelLists, "left");
		assertEquals("h", map[5][4]);
		assertEquals("f", map[5][5]);
		map[5][3] = "i";
		WarGameStartModel.characterMovement(mapModelLists, "left");
		assertEquals("h", map[5][4]);
		map[5][3] = "m";
		WarGameStartModel.characterMovement(mapModelLists, "left");
		assertEquals("h", map[5][4]);
		map[5][3] = "n";
		WarGameStartModel.characterMovement(mapModelLists, "left");
		assertEquals("h", map[5][4]);
		map[5][3] = "x";
		WarGameStartModel.characterMovement(mapModelLists, "left");
		assertEquals("h", map[5][4]);
	}
}
