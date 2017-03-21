package warGame.JUnitTest;

import java.util.ArrayList;

import junit.framework.TestCase;
import warGame.Model.WarGameMapModel;
import warGame.Model.WarGameStartModel;

public class StartModelTest5 extends TestCase{

	WarGameMapModel mapModel;
	
	public void setUp() throws Exception {
		System.out.println("Start Test5 begins");
		mapModel = new WarGameMapModel("test", 10, 10);
	}
	
	public void tearDown() throws Exception {
		System.out.println("Start Test5 ends");
		System.out.println();
	}
	
	public void testcharacterMovement(){
		System.out.println("Character moving up");
		String map[][] = mapModel.getMap();
		map[5][5] = "h";
		mapModel.setMap(map);
		ArrayList<WarGameMapModel> mapModelLists = new ArrayList<WarGameMapModel>();
		mapModelLists.add(mapModel);
		WarGameStartModel.characterMovement(mapModelLists, "up");
		assertEquals("h", map[4][5]);
		assertEquals("f", map[5][5]);
		map[3][5] = "i";
		WarGameStartModel.characterMovement(mapModelLists, "up");
		assertEquals("h", map[4][5]);
		map[3][5] = "m";
		WarGameStartModel.characterMovement(mapModelLists, "up");
		assertEquals("h", map[4][5]);
		map[3][5] = "n";
		WarGameStartModel.characterMovement(mapModelLists, "up");
		assertEquals("h", map[4][5]);
		map[3][5] = "x";
		WarGameStartModel.characterMovement(mapModelLists, "up");
		assertEquals("h", map[4][5]);
	}
}
