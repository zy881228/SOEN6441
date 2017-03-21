package warGame.JUnitTest;

import java.util.ArrayList;

import junit.framework.TestCase;
import warGame.Model.WarGameMapModel;
import warGame.Model.WarGameStartModel;

public class StartModelTest8 extends TestCase{

	WarGameMapModel mapModel;
	WarGameMapModel mapModel2;
	
	public void setUp() throws Exception {
		System.out.println("Start Test8 begins");
		mapModel = new WarGameMapModel("test", 10, 10);
		mapModel2 = new  WarGameMapModel("test2", 12, 12);
	}
	
	public void tearDown() throws Exception {
		System.out.println("Start Test8 ends");
		System.out.println();
	}
	
	public void testcharacterMovement(){
		System.out.println("Loading next map");
		String map[][] = mapModel.getMap();
		map[5][5] = "h";
		map[4][5] = "O";
		mapModel.setMap(map);
		ArrayList<WarGameMapModel> mapModelLists = new ArrayList<WarGameMapModel>();
		mapModelLists.add(mapModel);
		mapModelLists.add(mapModel2);
		String mapName = (WarGameStartModel.characterMovement(mapModelLists, "up")).getMapName();
		assertEquals(mapModel2.getMapName(), mapName);
	}
}
