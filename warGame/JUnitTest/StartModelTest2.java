package warGame.JUnitTest;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import warGame.Model.WarGameCharacterModel;
import warGame.Model.WarGameItemModel;
import warGame.Model.WarGameMapModel;
import warGame.Model.WarGameStartModel;
import junit.framework.TestCase;

public class StartModelTest2 extends TestCase{

	WarGameStartModel startModel = new WarGameStartModel();
	WarGameMapModel mapModel = new WarGameMapModel();
	public void setUp() throws Exception {
		System.out.println("Start Test2 begins");
		
	}
	
	public void tearDown() throws Exception {
		System.out.println("Start Test2 ends");
		System.out.println();
	}
	
	public void testSetAdaption() throws UnsupportedEncodingException, FileNotFoundException{
		System.out.println("Test the adaption of level");
		Map<String, WarGameMapModel> mapsByMap = WarGameMapModel.listAllMaps();	
		mapModel = mapsByMap.get("1");
		WarGameItemModel itemModel = new WarGameItemModel();
		int level = 20;
		mapModel = startModel.setAdaption(mapModel, level);
		for(WarGameCharacterModel characterModel_buffer : mapModel.getContainFriends())
		{
			int result = characterModel_buffer.getLevel();
			assertEquals(20, result);
			String equip[] = characterModel_buffer.getEquip();
			String backpack[] = characterModel_buffer.getBackpack();
			for(int i =0;i<7;i++)
			{
				if(!equip[i].equals("null"))
				{
					String str[] = equip[i].trim().split(" ");
					assertEquals(5, str[2]);
				}
						
			}
			for(int i =0;i<10;i++)
			{
				if(!backpack[i].equals("null"))
				{
					String str[] = backpack[i].trim().split(" ");
					assertEquals(5, str[2]);
				}
						
			}
		}
		for(WarGameCharacterModel characterModel_buffer : mapModel.getContainEnemies())
		{
			int result = characterModel_buffer.getLevel();
			assertEquals(20, result);
			String equip[] = characterModel_buffer.getEquip();
			String backpack[] = characterModel_buffer.getBackpack();
			for(int i =0;i<7;i++)
			{
				if(!equip[i].equals("null"))
				{
					String str[] = equip[i].trim().split(" ");
					assertEquals(5, str[2]);
				}
						
			}
			for(int i =0;i<10;i++)
			{
				if(!backpack[i].equals("null"))
				{
					String str[] = backpack[i].trim().split(" ");
					assertEquals("5", str[2]);
				}
						
			}
		}
		for(WarGameItemModel itemModel_buffer : mapModel.getContainItems())
		{
			String result = itemModel_buffer.getEnchanNumber();
			assertEquals("5", result);
		}
	}
}
