package warGame;

import junit.framework.TestCase;

public class CharacterModelTest2 extends TestCase{

	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	WarGameItemModel itemModel = new WarGameItemModel();
	WarGameItemModel itemModel2 = new WarGameItemModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Character Test2 begins");
		characterModel.createCharacter();
		itemModel.createItem("Ring","Strength","3");
		itemModel2.createItem("Ring","Wisdom","3");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Character Test2 ends");
		System.out.println();
	}
	
	public void testSetEquipChanged(){
		System.out.println("character cannot wear more than one item of each kind");
		String score[] = new String[2];
		String changeBefore = new String();
		String changeAfter = new String();
		String scoreWithItem1[] = new String[18];
		String scoreWithItem2[] = new String[18];
		String itemType = itemModel.getItemType();
		String itemType2 = itemModel2.getItemType();
		String enchanType = itemModel.getEnchanType();
		String enchanType2 = itemModel2.getEnchanType();
		String enchanNum = itemModel.getEnchanNumber();
		String enchanNum2 = itemModel2.getEnchanNumber();
		//equip item 1
		changeAfter = itemType+" "+enchanType+" "+enchanNum;
		characterModel.setEquipChanged(null,changeAfter);
		for(int i=0;i<18;i++)
			{
				score = characterModel.getScore(i);
				scoreWithItem1[i] = score[1];
			}
		
		//equip item 2
		changeBefore = itemType2+" "+enchanType2+" "+enchanNum2;
		characterModel.setEquipChanged(changeAfter,changeBefore);
		for(int i=0;i<18;i++)
		{
			score = characterModel.getScore(i);
			scoreWithItem2[i] = score[1];
		}
		scoreWithItem1[1] = Integer.parseInt(scoreWithItem1[1])-3+"";
		scoreWithItem1[12] = Integer.parseInt(scoreWithItem1[12])-3+"";
		int damage = characterModel.calDamage(Integer.parseInt(scoreWithItem1[12]));
		scoreWithItem1[10] = damage+"";
		scoreWithItem1[5] = Integer.parseInt(scoreWithItem1[5])+3+"";
		scoreWithItem1[16] = Integer.parseInt(scoreWithItem1[16])+3+"";

		for(int i=0;i<18;i++)
		{
			assertEquals(scoreWithItem1[i], scoreWithItem2[i]);
		}
	}
}