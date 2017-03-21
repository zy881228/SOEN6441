package warGame.JUnitTest;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import warGame.Model.WarGameCharacterModel;
import warGame.Model.WarGameItemModel;
import junit.framework.TestCase;

public class CharacterModelTest5 extends TestCase{

	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	WarGameItemModel itemModel = new WarGameItemModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Character Test5 begins");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Character Test5 ends");
		System.out.println();
	}
	
	public void testModifierChange() throws UnsupportedEncodingException, FileNotFoundException{
		System.out.println("Modifier change when the edit the abilities");
		characterModel.loadCharacterJson("1");
		String newMessage[]= new String[8];
		String result = new String();
		String resultExp[] = new String[6];
		String resultAct[] = new String[6];
		for(int i=0;i<8;i++)
		{
			newMessage[i] = "1";
		}
		result = characterModel.modifierChange(newMessage);
		for(int i=0;i<6;i++)
		{
			resultExp[i] = "-5";
		}
		String str[] = result.trim().split(" ");
		for(int i=0;i<6;i++)
		{
			resultAct[i] = str[i+5];
			assertEquals(resultExp[i], resultAct[i]);
		}
	}
}
