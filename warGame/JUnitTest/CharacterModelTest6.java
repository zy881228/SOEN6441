package warGame.JUnitTest;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import warGame.Model.WarGameCharacterModel;
import warGame.Model.WarGameItemModel;
import junit.framework.TestCase;

public class CharacterModelTest6 extends TestCase{

	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	WarGameItemModel itemModel = new WarGameItemModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Character Test6 begins");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Character Test6 ends");
		System.out.println();
	}
	
	public void testSetItem() throws UnsupportedEncodingException, FileNotFoundException{
		System.out.println("Set the item to the backpack");
		characterModel.loadCharacterJson("1");
		String equip[]= new String[7];
		String backpack[]= new String[10];
		String item = "Helmet Wisdom 1";
		String resultExp[] = new String[6];
		String resultAct[] = new String[6];
		int position = 0;
		backpack = characterModel.getBackpack();
		for(int i=0;i<10;i++)
		{
			if(backpack[i].equals("null"))
			{
				position = i;
				break;
			}
		}
		characterModel.setItem("item1 "+item);
		backpack = characterModel.getBackpack();		
		assertEquals(item, backpack[position]);
		
	}
}
