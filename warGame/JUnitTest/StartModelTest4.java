package warGame.JUnitTest;

import java.util.ArrayList;

import junit.framework.TestCase;
import warGame.Model.WarGameMapModel;
import warGame.Model.WarGameStartModel;

public class StartModelTest4 extends TestCase{

	WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Start Test4 begins");
		mapModel = new WarGameMapModel("test", 10, 10);
	}
	
	public void tearDown() throws Exception {
		System.out.println("Start Test4 ends");
		System.out.println();
	}
	
	public void testcharacterMovement(){
		System.out.println("Character moving to right");
		String map[][] = mapModel.getMap();
		map[5][5] = "h";
		mapModel.setMap(map);
		ArrayList<WarGameMapModel> mapModelLists = new ArrayList<WarGameMapModel>();
		mapModelLists.add(mapModel);
		WarGameStartModel.characterMovement(mapModelLists, "right");
		assertEquals("h", map[5][6]);
		assertEquals("f", map[5][5]);
		map[5][7] = "i";
		WarGameStartModel.characterMovement(mapModelLists, "right");
		assertEquals("h", map[5][6]);
		map[5][7] = "m";
		WarGameStartModel.characterMovement(mapModelLists, "right");
		assertEquals("h", map[5][6]);
		map[5][7] = "n";
		WarGameStartModel.characterMovement(mapModelLists, "right");
		assertEquals("h", map[5][6]);
		map[5][7] = "x";
		WarGameStartModel.characterMovement(mapModelLists, "right");
		assertEquals("h", map[5][6]);
	}
}
