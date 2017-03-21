package warGame.JUnitTest;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import warGame.Model.WarGameCharacterModel;
import warGame.Model.WarGameItemModel;
import junit.framework.TestCase;

public class CharacterModelTest8 extends TestCase{

	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	WarGameItemModel itemModel = new WarGameItemModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Character Test8 begins");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Character Test8 ends");
		System.out.println();
	}
	
	public void testGetChangeBefore() throws UnsupportedEncodingException, FileNotFoundException{
		System.out.println("Get the before info of the equip");
		characterModel.loadCharacterJson("1");
		String equip[]= new String[7];
		String item = "Helmet Wisdom 1";
		String resultExp = new String();
		String resultAct = new String();
		equip = characterModel.getEquip();
		if(equip[0].equals("null"))
		{
			resultExp = null;
		}
		else
		{
			resultExp = equip[0];
		}
		resultAct = characterModel.getChangeBefore(item);
		assertEquals(resultExp, resultAct);
	}
}
