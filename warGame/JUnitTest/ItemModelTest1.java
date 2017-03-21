package warGame.JUnitTest;

import java.io.IOException;

import warGame.Model.WarGameItemModel;
import junit.framework.TestCase;

public class ItemModelTest1 extends TestCase{

	WarGameItemModel itemModel = new WarGameItemModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Item Test1 begins");
		itemModel.loadItemJson("1");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Item Test1 ends");
		System.out.println();
	}
	
	public void testEditItem() throws IOException{
		System.out.println("Test edit item");
		String changeBefore = new String();
		String changeAfter = new String();
		String itemID = itemModel.getItemID();
		String newItemType = "Boots";
		String newEnchanType = "Armor_class";
		String newEnchanNum = "1";
		itemModel.editItemJson(itemID,newItemType,newEnchanType,newEnchanNum);
		itemModel.loadItemJson(itemID);
		String itemType = itemModel.getItemType();
		String enchanType = itemModel.getEnchanType();		
		String enchanNum = itemModel.getEnchanNumber();
		assertEquals(newItemType, itemType);
		assertEquals(newEnchanType, enchanType);
		assertEquals(newEnchanNum, enchanNum);
	}
}
