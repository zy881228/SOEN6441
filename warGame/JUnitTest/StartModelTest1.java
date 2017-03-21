package warGame.JUnitTest;

import warGame.Model.WarGameStartModel;
import junit.framework.TestCase;

public class StartModelTest1 extends TestCase{

	WarGameStartModel startModel = new WarGameStartModel();
	
	public void setUp() throws Exception {
		System.out.println("Start Test1 begins");
		
	}
	
	public void tearDown() throws Exception {
		System.out.println("Start Test1 ends");
		System.out.println();
	}
	
	public void testCheckBackPack(){
		System.out.println("loot chest");
		String newBackPack[] = new String[10];
		Boolean resultExp = true;
		Boolean resultAct = true;
		for(int i=0;i<10;i++)
		{
			newBackPack[i] = "null";
		}
		resultAct = startModel.checkBackpack(newBackPack);
		assertEquals(resultExp, resultAct);
		
		for(int i=0;i<10;i++)
		{
			newBackPack[i] = "Helmet Wisdom 1";
		}
		resultAct = startModel.checkBackpack(newBackPack);
		resultExp = false;
		assertEquals(resultExp, resultAct);
	}
}
