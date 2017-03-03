package warGame;

import java.io.IOException;

import junit.framework.TestCase;

public class CharacterModelTest4 extends TestCase{

	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Character Test4 begins");
		characterModel.loadChar("Character1");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Character Test4 ends");
		System.out.println();
	}
	
	public void testModifierChange() throws IOException{
		System.out.println("Test modifier change with ability change");
		String score[] = new String[2];
		String changeBefore = new String();
		String changeAfter = new String();
		String scoreBefore[] = new String[18];
		String scoreAfter[] = new String[8];
		int scoreModifier[] = new int[6];
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
		for(int i=1;i<7;i++)
		{
			scoreAfter[i] = "1";
		}
		for(int i=0;i<6;i++)
		{
			scoreModifier[i] = -5;
		}
		
		characterModel.replaceCharacter(scoreAfter);
		characterModel.loadChar("Character"+characterID);
		for(int i=0;i<18;i++)
		{
			score = characterModel.getScore(i);
			scoreBefore[i] = score[1];
		}
		for(int i=0;i<6;i++)
		{
			assertEquals(scoreModifier[i]+"", scoreBefore[i+12]);
		}
		
	
	}
}
