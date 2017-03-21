package warGame.JUnitTest;

import java.io.IOException;

import warGame.Model.WarGameCharacterModel;
import junit.framework.TestCase;

public class CharacterModelTest3 extends TestCase{

	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Character Test3 begins");
		characterModel.loadCharacterJson("2");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Character Test3 ends");
		System.out.println();
	}
	
	public void testEditCharacter() throws IOException{
		System.out.println("Test edit character");
		String score[] = new String[2];
		String changeBefore = new String();
		String changeAfter = new String();
		String scoreBefore[] = new String[18];
		String scoreAfter[] = new String[8];
		String scoreReLoad[] = new String[18];
		String characterID = characterModel.getCharacterID();
		String equip[] = characterModel.getEquipID();
		String backpack[] =characterModel.getBackpackID();
		int picNum = characterModel.getPicNumber();
		String message = new String();
		for(int i=0;i<18;i++)
		{
			score = characterModel.getScore(i);
			scoreBefore[i] = score[1];
		}
		for(int i=0;i<7;i++)
		{
			scoreAfter[i+1] = scoreBefore[i];
		}
		scoreAfter[0] = characterID;
		for(int i=1;i<8;i++)
		{
			scoreAfter[i] = "2";
		}
		
		
		characterModel.editCharacterJson(scoreAfter);
		characterModel.loadCharacterJson(characterID);
		for(int i=0;i<18;i++)
		{
			score = characterModel.getScore(i);
			scoreBefore[i] = score[1];
		}
		for(int i=1;i<8;i++)
		{
			//System.out.println("exp:"+scoreAfter[i]+" "+i);
			//System.out.println("act:"+scoreBefore[i]+" "+i);
			assertEquals(scoreAfter[i], scoreBefore[i-1]);
		}
		
	
	}
}
