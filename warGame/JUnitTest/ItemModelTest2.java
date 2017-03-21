package warGame.JUnitTest;

import java.io.IOException;

import warGame.Model.WarGameItemModel;
import junit.framework.TestCase;

public class ItemModelTest2 extends TestCase{

	WarGameItemModel itemModel = new WarGameItemModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Item Test2 begins");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Item Test2 ends");
		System.out.println();
	}
	
	public void testItemAdaption() throws IOException{
		System.out.println("Test item adaption according to the level change");
		int level = 20;
		String resultExp = new String();
		String resultAct = new String();
		itemModel.loadItemJson("1");
		resultAct = itemModel.itemAdaption(level);
		resultExp = "5";
		assertEquals(resultExp, resultAct);
	}
}
