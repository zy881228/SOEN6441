package warGame.JUnitTest;

import java.io.IOException;

import warGame.Model.WarGameItemModel;
import junit.framework.TestCase;

public class ItemModelTest3 extends TestCase{

	WarGameItemModel itemModel = new WarGameItemModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Item Test3 begins");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Item Test3 ends");
		System.out.println();
	}
	
	public void testCreateItem() throws IOException{
		System.out.println("Test item creation");
		String newItemType = "Boots";
		String newEnchanType = "Armor_class";
		String newEnchanNum = "1";
		String resultItemType = new String();
		String resultEnchanType = new String();
		String resultEnchanNum = new String();
		String itemID = new String();
		itemModel.createItem(newItemType, newEnchanType, newEnchanNum);
		itemID = itemModel.getItemID();
		itemModel.saveItemJson(itemModel);
		itemModel.loadItemJson(itemID);
		resultItemType = itemModel.getItemType();
		resultEnchanType = itemModel.getEnchanType();
		resultEnchanNum = itemModel.getEnchanNumber();
		assertEquals(newItemType, resultItemType);
		assertEquals(newEnchanType, resultEnchanType);
		assertEquals(newEnchanNum, resultEnchanNum);
		
	}
}
