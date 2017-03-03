package warGame;

import junit.framework.TestCase;

public class CharacterModelTest1 extends TestCase{

	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	WarGameItemModel itemModel = new WarGameItemModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Character Test1 begins");
		characterModel.createCharacter();
		itemModel.createItem("Ring","Strength","3");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Character Test1 ends");
		System.out.println();
	}
	
	public void testSetEquipChanged(){
		System.out.println("Equip or unequip will affect ability scores");
		String score[] = new String[2];
		String changeBefore = new String();
		String changeAfter = new String();
		String scoreBefore[] = new String[18];
		String scoreAfter[] = new String[18];
		String itemType = itemModel.getItemType();
		String enchanType = itemModel.getEnchanType();		
		String enchanNum = itemModel.getEnchanNumber();
		for(int i=0;i<18;i++)
		{
			score = characterModel.getScore(i);
			scoreBefore[i] = score[1];
		}
		//equip
		scoreBefore[1] = Integer.parseInt(scoreBefore[1])+3+"";
		scoreBefore[12] = Integer.parseInt(scoreBefore[12])+3+"";
		int damage = characterModel.calDamage(Integer.parseInt(scoreBefore[12]));
		scoreBefore[10] = damage+"";
		changeAfter = itemType+" "+enchanType+" "+enchanNum;
		characterModel.setEquipChanged(null,changeAfter);
		for(int i=0;i<18;i++)
		{
			score = characterModel.getScore(i);
			scoreAfter[i] = score[1];
			assertEquals(scoreBefore[i], scoreAfter[i]);
		}
		//unequip
		scoreBefore[1] = Integer.parseInt(scoreBefore[1])-3+"";
		scoreBefore[12] = Integer.parseInt(scoreBefore[12])-3+"";
		damage = characterModel.calDamage(Integer.parseInt(scoreBefore[12]));
		scoreBefore[10] = damage+"";
		characterModel.setEquipChanged(changeAfter,null);
		for(int i=0;i<18;i++)
		{
			score = characterModel.getScore(i);
			scoreAfter[i] = score[1];
			assertEquals(scoreBefore[i], scoreAfter[i]);
		}
	}
}
