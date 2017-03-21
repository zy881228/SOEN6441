package warGame.JUnitTest;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import warGame.Model.WarGameCharacterModel;
import warGame.Model.WarGameItemModel;
import junit.framework.TestCase;

public class CharacterModelTest7 extends TestCase{

	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	WarGameItemModel itemModel = new WarGameItemModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Character Test7 begins");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Character Test7 ends");
		System.out.println();
	}
	
	public void testSetItemEquip() throws UnsupportedEncodingException, FileNotFoundException{
		System.out.println("Set the item to the equip");
		characterModel.loadCharacterJson("1");
		String equip[]= new String[7];
		String item = "Helmet Wisdom 1";
		String resultExp[] = new String[6];
		String resultAct[] = new String[6];
		int position = 0;
		characterModel.setItemEquip(item);
		equip = characterModel.getEquip();
		assertEquals(item, equip[0]);
	}
}
